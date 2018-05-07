package inventormod.powers;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class GeneratorPower extends AbstractPower {
    public static final String POWER_ID = "Generator";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public GeneratorPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = ImageMaster.loadImage("img/powers/generator.png");
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        if (this.owner.isPlayer) {
            int amount = Math.min(this.amount, FuelPower.currentAmount(this.owner));
            if (amount > 0) {
                AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, FuelPower.POWER_ID, amount));
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.amount));
                this.flash();
            }
        }
    }
}
