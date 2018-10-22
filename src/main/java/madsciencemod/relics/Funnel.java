package madsciencemod.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Funnel extends AbstractMadScienceRelic {
    public static final String ID = "MadScienceMod:Funnel";

    public Funnel() {
        super(ID, RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Funnel();
    }

    // Note: Actual effect happens in GainFuelAction
}

