package inventormod.relics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import basemod.abstracts.CustomRelic;
import inventormod.InventorMod;

public abstract class AbstractInventorRelic extends CustomRelic {
    public AbstractInventorRelic(String id, RelicTier tier, LandingSound landingSound) {
        super(id, new Texture(Gdx.files.internal(InventorMod.relicImage(id))), new Texture(Gdx.files.internal(InventorMod.relicOutline(id))), tier, landingSound);
    }
}

