package inventormod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import inventormod.actions.common.GainFuelAction;

public class ReconstitutePower extends AbstractMadSciencePower implements FuelPower.Listener {
    public static final String POWER_ID = "Reconstitute";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ReconstitutePower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, owner, amount);
        this.updateDescription();
        this.isTurnBased = true;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + FuelPower.fuelSpentThisTurn + DESCRIPTIONS[2];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (FuelPower.fuelSpentThisTurn > 0) {
            this.flash();
            int gain = FuelPower.fuelSpentThisTurn * amount;
            AbstractDungeon.actionManager.addToBottom(new GainFuelAction(gain));
        }
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    @Override
    public void onFuelChange(int amount) {
        updateDescription();
    }
}
