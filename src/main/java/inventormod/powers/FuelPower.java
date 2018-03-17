package inventormod.powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FuelPower extends AbstractPower {
    public static final String POWER_ID = "Fuel";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public FuelPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = ImageMaster.loadImage("img/powers/fuel.png");
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
    
    public static int currentAmount(AbstractPlayer p) {
        AbstractPower power = p.getPower(POWER_ID);
        return power == null ? 0 : power.amount;
    }
    public static boolean  haveEnough(AbstractPlayer p, int amount) {
        return currentAmount(p) >= amount;
    }
}
