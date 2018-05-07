package inventormod.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import inventormod.InventorMod;
import inventormod.actions.unique.BombExplodeAction;

public class BombPower extends AbstractPower {
    public static final String POWER_ID = "Bomb";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final int TURNS = 3;

    public int amounts[];
    public int minturns, maxturns;

    public BombPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount; // needed for stackAmount to work, but later on we want to display minturns
        this.amounts = new int[TURNS+1];
        this.amounts[TURNS] = amount;
        this.minturns = this.maxturns = TURNS;
        this.type = AbstractPower.PowerType.DEBUFF;
        this.isTurnBased = true;
        this.updateDescription();
        this.img = ImageMaster.loadImage(InventorMod.powerImage(POWER_ID));
    }

    @Override
    public void updateDescription() {
        this.description = "";
        for (int i = 0; i<TURNS+1; ++i) {
            if (amounts[i] > 0) {
                if (i > minturns) this.description += " ";
                this.description += DESCRIPTIONS[0] + amounts[i] + DESCRIPTIONS[1] + i + (i == 1 ? DESCRIPTIONS[2] : DESCRIPTIONS[3]);
            }
        }
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        // render number of turns
        this.amount = minturns;
        super.renderAmount(sb,x,y,c);
    }

    @Override
    public void stackPower(int stackAmount) {
        amounts[TURNS] += stackAmount;
    }

    public int totalAmount() {
        int total = 0;
        for (int i = 0 ; i < amounts.length ; i++) {
            total += amounts[i];
        }
        return total;
    }

    @Override
    public void atStartOfTurn() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flashWithoutSound();
            for (int i = 1; i < amounts.length; ++i) {
                amounts[i-1] = amounts[i];
            }
            amounts[amounts.length-1] = 0;
            if (amounts[0] > 0) {
                AbstractDungeon.actionManager.addToBottom(new BombExplodeAction(this.owner, this.owner, amounts[0]));
                amounts[0] = 0;
            }
            // smallest and largest turn with nonzero amount
            minturns = TURNS+1;
            maxturns = 0;
            for (int i = 0; i < amounts.length; ++i) {
                if (amounts[i] > 0) {
                    minturns = Math.min(minturns, i);
                    maxturns = Math.max(maxturns, i);
                }
            }
            this.amount = minturns; // turns remaining
            if (maxturns == 0) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            } else {
                this.updateDescription();
            }
        }
    }
}
