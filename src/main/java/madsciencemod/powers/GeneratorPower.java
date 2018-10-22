package madsciencemod.powers;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class GeneratorPower extends AbstractMadSciencePower {
    public static final String POWER_ID = "MadScienceMod:Generator";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public GeneratorPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, owner, amount);
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

    @Override
    public void atStartOfTurn() {
        if (this.owner.isPlayer) {
            int amount = Math.min(this.amount, FuelPower.currentAmount(this.owner));
            if (amount > 0) {
                FuelPower.spendFuel(amount);
                AbstractDungeon.actionManager.addToBottom(new SFXAction("ORB_PLASMA_EVOKE", 0.1f));
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.amount));
                this.flash();
            }
        }
    }
}
