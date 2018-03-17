package inventormod.characters;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.abstracts.CustomPlayer;
import inventormod.patches.PlayerClassEnum;

public class Inventor extends CustomPlayer {
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("Inventor");
    public static final String[] NAMES = characterStrings.NAMES;
    public static final String[] TEXT = characterStrings.TEXT;
    // Note: Using getCharacterString here doesn't work, because they are loaded after character creation by basemod
    //public static final String[] NAMES = {"The Inventor."};
    //public static final String[] TEXT = {"This tinkerer relies on his many machines and devices to aid him."};
    public static final int START_HP = 70 / 2;
    public static final int CARD_DRAW = 5;
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

    public Inventor(String name, AbstractPlayer.PlayerClass setClass) {
        super(name, setClass, orbTextures, "img/characters/inventor/orb/vfx.png", null, null);
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
        retVal.add("Strike_Bronze");
        retVal.add("Strike_Bronze");
        retVal.add("Strike_Bronze");
        retVal.add("Strike_Bronze");
        retVal.add("Strike_Bronze");
        retVal.add("Defend_Bronze");
        retVal.add("Defend_Bronze");
        retVal.add("Defend_Bronze");
        retVal.add("Defend_Bronze");
        retVal.add("Defend_Bronze");
        retVal.add("Powered Strike");
        retVal.add("Powered Defend");
        return retVal;
    }

    public static ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<String>();
        retVal.add("Fuel Tank");
        UnlockTracker.markRelicAsSeen("Fuel Tank");
        return retVal;
    }

    public static CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0], START_HP, START_HP, START_GOLD, CARD_DRAW, PlayerClassEnum.INVENTOR, getStartingRelics(), getStartingDeck(), false);
    }
}

