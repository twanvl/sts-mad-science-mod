package madsciencemod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class PowerArmorPower extends AbstractMadSciencePower {
    public static final String POWER_ID = "PowerArmor";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public PowerArmorPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, owner, amount);
        this.updateDescription();
        this.priority = 99;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        int amount = FuelPower.currentAmount(this.owner);
        if (amount > 0) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, this.amount * amount));
        }
    }
}
