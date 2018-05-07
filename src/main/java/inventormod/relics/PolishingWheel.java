package inventormod.relics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import inventormod.InventorMod;

public class PolishingWheel extends CustomRelic {
    public static final String ID = "PolishingWheel";
    private static final String IMAGE = InventorMod.relicImage(ID);

    public PolishingWheel() {
        super(ID, new Texture(Gdx.files.internal(IMAGE)), RelicTier.UNCOMMON, LandingSound.SOLID);
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
