package inventormod.characters;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.abstracts.CustomPlayer;
import inventormod.cards.Defend_Bronze;
import inventormod.cards.HideBehindJunk;
import inventormod.cards.PoweredStrike;
import inventormod.cards.Strike_Bronze;
import inventormod.patches.PlayerClassEnum;
import inventormod.relics.FuelTank;

public class MadScientist extends CustomPlayer {
    //private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("MadScientist");
    //public static final String[] NAMES = characterStrings.NAMES;
    //public static final String[] TEXT = characterStrings.TEXT;
    // Note: Using getCharacterString here doesn't work, because they are loaded after character creation by basemod
    public static final String[] NAMES = {"Mad Scientist","The Mad Scientist"};
    public static final String[] TEXT = {"You have to be mad to climb this spire while relying on\nexperimental gadgets and unstable explosives. And he certainly is that."};
    public static final int START_HP = 75;
    public static final int CARD_DRAW = 5;
    public static final int MAX_ORBS = 0;
    public static final int ENERGY = 3;
    public static final int START_GOLD = 99;

	public static final String[] orbTextures = {
        "img/characters/inventor/orb/layer1.png",
        "img/characters/inventor/orb/layer2.png",
        "img/characters/inventor/orb/layer3.png",
        "img/characters/inventor/orb/layer4.png",
        "img/characters/inventor/orb/layer5.png",
        "img/characters/inventor/orb/layer6.png",
        "img/characters/inventor/orb/layer1d.png",
        "img/characters/inventor/orb/layer2d.png",
        "img/characters/inventor/orb/layer3d.png",
        "img/characters/inventor/orb/layer4d.png",
        "img/characters/inventor/orb/layer5d.png",
    };

    public MadScientist(String name, AbstractPlayer.PlayerClass setClass) {
        super(name, setClass, orbTextures, "img/characters/inventor/orb/vfx.png", (String)null, null);
        this.initializeClass(null,
            "img/characters/inventor/shoulder2.png",
            "img/characters/inventor/shoulder.png",
            "img/characters/inventor/corpse.png",
            getLoadout(), 20.0f, -10.0f, 220.0f, 290.0f, new EnergyManager(ENERGY));
        this.loadAnimation("img/characters/inventor/idle/skeleton.atlas", "img/characters/inventor/idle/skeleton.json", 1.0f);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public static ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<String>();
        retVal.add(Strike_Bronze.ID);
        retVal.add(Strike_Bronze.ID);
        retVal.add(Strike_Bronze.ID);
        retVal.add(Strike_Bronze.ID);
        retVal.add(Defend_Bronze.ID);
        retVal.add(Defend_Bronze.ID);
        retVal.add(Defend_Bronze.ID);
        retVal.add(Defend_Bronze.ID);
        retVal.add(PoweredStrike.ID);
        retVal.add(HideBehindJunk.ID);
        return retVal;
    }

    public static ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<String>();
        retVal.add(FuelTank.ID);
        UnlockTracker.markRelicAsSeen(FuelTank.ID);
        return retVal;
    }

    public static CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0], START_HP, START_HP, MAX_ORBS, START_GOLD, CARD_DRAW, PlayerClassEnum.MAD_SCIENTIST, getStartingRelics(), getStartingDeck(), false);
    }
}

