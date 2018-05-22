package madsciencemod.relics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import basemod.abstracts.CustomRelic;
import madsciencemod.MadScienceMod;

public abstract class AbstractMadScienceRelic extends CustomRelic {
    public AbstractMadScienceRelic(String id, RelicTier tier, LandingSound landingSound) {
        super(id, new Texture(Gdx.files.internal(MadScienceMod.relicImage(id))), new Texture(Gdx.files.internal(MadScienceMod.relicOutline(id))), tier, landingSound);
    }
}

