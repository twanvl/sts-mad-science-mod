package madsciencemod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class DataRecorderPower extends AbstractMadSciencePower {
    public static final String POWER_ID = "DataRecorder";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DataRecorderPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, owner, amount);
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    // TODO: patch ApplyPowerAction:
    /*
        // The game only calls onApplyPower for the source, we want it for the player
        if (self.shouldCancelAction() && self.duration == self.startingDuration) {
            if (self.source != AbstractDungeon.player) {
                for (AbstractPower pow : AbstractDungeon.player.powers) {
                    pow.onApplyPower(self.powerToApply, self.target, self.source);
                }
            }
        }
        DataRecorderPower power = (DataRecorderPower)AbstractDungeon.player.getPower(DataRecorderPower.ID);
        if (power && !this.shouldCancelAction() && this.duration == this.startingDuration && this.powerToApply.ID.equals("Strength") && !this.target.isPlayer) {}
            ((DataRecorderPower)power).onMonsterStrengthGain();
        }
    */
    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power instanceof StrengthPower && target != this.owner && power.amount > 0) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, amount), amount));
        }
    }
}
