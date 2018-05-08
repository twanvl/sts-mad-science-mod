package inventormod.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Funnel extends AbstractInventorRelic {
    public static final String ID = "Funnel";

    public Funnel() {
        super(ID, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Funnel();
    }

    // Note: Actual effect happens in GainFuelAction
}
