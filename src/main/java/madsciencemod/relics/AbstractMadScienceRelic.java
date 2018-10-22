package madsciencemod.relics;

import com.megacrit.cardcrawl.helpers.ImageMaster;

import basemod.abstracts.CustomRelic;
import madsciencemod.MadScienceMod;

public abstract class AbstractMadScienceRelic extends CustomRelic {
    public AbstractMadScienceRelic(String id, RelicTier tier, LandingSound landingSound) {
        super(id, "", tier, landingSound);
        img = ImageMaster.loadImage(MadScienceMod.relicImage(id));
        largeImg = ImageMaster.loadImage(MadScienceMod.relicLargeImage(id));
        outlineImg = ImageMaster.loadImage(MadScienceMod.relicOutlineImage(id));
    }
}

