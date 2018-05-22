package inventormod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class LightOnFirePower extends AbstractMadSciencePower implements FuelPower.Listener {
    public static final String POWER_ID = "LightOnFire";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public int damage;

    public LightOnFirePower(AbstractCreature owner, int damage, int amount) {
        super(POWER_ID, NAME, owner, amount);
        this.damage = damage;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.damage + DESCRIPTIONS[1];
    }

    @Override
    public void onFuelChange(int amount) {
        if (amount < 0) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new DamageAction(this.owner, new DamageInfo(this.owner, this.damage), AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void atEndOfRound() {
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        }
    }
}
