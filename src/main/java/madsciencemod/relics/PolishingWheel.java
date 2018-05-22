package madsciencemod.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;

public class PolishingWheel extends AbstractMadScienceRelic {
    public static final String ID = "PolishingWheel";

    public PolishingWheel() {
        super(ID, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PolishingWheel();
    }

    // Note: The actual effect happens in ShuffleTrinketAction
}
