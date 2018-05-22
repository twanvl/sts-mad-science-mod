package inventormod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FuelPower extends AbstractMadSciencePower {
    public static final String POWER_ID = "Fuel";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String NOT_ENOUGH_FUEL_MESSAGE = DESCRIPTIONS[1];

    public static int fuelSpentThisCombat = 0;
    public static int fuelSpentThisTurn = 0;

    public FuelPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, owner, amount);
        this.priority = 1; // always first
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    /*@Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        Color redColor = new Color(1.0f, 0.0f, 0.0f, 1.0f);
        redColor.a = c.a;
        c = redColor;
        FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount), x, y, this.fontScale, c);
    }*/

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
        if (this.amount >= 999) {
            this.amount = 999;
        }
        if (this.amount <= 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
        publishFuelChange(stackAmount);
    }

    @Override
    public void onInitialApplication() {
        publishFuelChange(this.amount);
    }

    @Override
    public void reducePower(int reduceAmount) {
        stackPower(-reduceAmount);
    }

    public static interface Listener {
        public void onFuelChange(int amount);
    }

    private void publishFuelChange(int gainAmount) {
        if (gainAmount < 0) {
            fuelSpentThisCombat += -gainAmount;
            fuelSpentThisTurn += -gainAmount;
        }
        for (AbstractPower p : owner.powers) {
            if (p instanceof Listener) {
                ((Listener)p).onFuelChange(gainAmount);
            }
        }
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            for (AbstractPower p : m.powers) {
                if (p instanceof Listener) {
                    ((Listener)p).onFuelChange(gainAmount);
                }
            }
        }
        if (AbstractDungeon.player != null) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c instanceof Listener) ((Listener)c).onFuelChange(gainAmount);
            }
            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                if (c instanceof Listener) ((Listener)c).onFuelChange(gainAmount);
            }
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (c instanceof Listener) ((Listener)c).onFuelChange(gainAmount);
            }
        }
    }

    public static void atPreBattle() {
        fuelSpentThisCombat = 0;
        fuelSpentThisTurn = 0;
    }

    @Override
    public void atStartOfTurn() {
        fuelSpentThisTurn = 0;
    }

    public static void atBattleEnd() {
        // We need to reset counts, otherwise card selection screen shows reduced cost of FumeBlast
        atPreBattle();
    }

    public static int currentAmount(AbstractCreature p) {
        AbstractPower power = p.getPower(POWER_ID);
        return power == null ? 0 : power.amount;
    }
    public static int currentAmount() {
        return currentAmount(AbstractDungeon.player);
    }

    public static void spendFuel(int amount) {
        AbstractPower p = AbstractDungeon.player.getPower(FuelPower.POWER_ID);
        if (p != null) p.reducePower(amount);
    }
}
