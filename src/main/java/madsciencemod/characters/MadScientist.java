package madsciencemod.characters;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.abstracts.CustomPlayer;
import madsciencemod.MadScienceMod;
import madsciencemod.cards.Defend_MadScience;
import madsciencemod.cards.HideBehindJunk;
import madsciencemod.cards.PoweredStrike;
import madsciencemod.cards.Strike_MadScience;
import madsciencemod.patches.CardColorEnum;
import madsciencemod.patches.PlayerClassEnum;
import madsciencemod.relics.FuelTank;

public class MadScientist extends CustomPlayer {
    //private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("MadScientist");
    //public static final String[] NAMES = characterStrings.NAMES;
    //public static final String[] TEXT = characterStrings.TEXT;
    // Note: Using getCharacterString here doesn't work, because they are loaded after character creation by basemod
    public static final String[] NAMES = {"Mad Scientist","The Mad Scientist"};
    public static final String[] TEXT = {"You have to be mad to climb this spire while relying on NL experimental gadgets and unstable explosives. And he certainly is that."};
    public static final int START_HP = 75;
    public static final int CARD_DRAW = 5;
    public static final int MAX_ORBS = 0;
    public static final int ENERGY = 3;
    public static final int START_GOLD = 99;

    public static final String[] orbTextures = {
        "img/characters/MadScientist/orb/layer1.png",
        "img/characters/MadScientist/orb/layer2.png",
        "img/characters/MadScientist/orb/layer3.png",
        "img/characters/MadScientist/orb/layer4.png",
        "img/characters/MadScientist/orb/layer5.png",
        "img/characters/MadScientist/orb/layer6.png",
        "img/characters/MadScientist/orb/layer1d.png",
        "img/characters/MadScientist/orb/layer2d.png",
        "img/characters/MadScientist/orb/layer3d.png",
        "img/characters/MadScientist/orb/layer4d.png",
        "img/characters/MadScientist/orb/layer5d.png",
    };

    public MadScientist(String name) {
        super(name, PlayerClassEnum.MAD_SCIENTIST, orbTextures, "img/characters/MadScientist/orb/vfx.png", (String)null, null);
        this.initializeClass(null,
            "img/characters/MadScientist/shoulder2.png",
            "img/characters/MadScientist/shoulder.png",
            "img/characters/MadScientist/corpse.png",
            getLoadout(), 20.0f, -10.0f, 220.0f, 290.0f, new EnergyManager(ENERGY));
        this.loadAnimation("img/characters/MadScientist/idle/skeleton.atlas", "img/characters/MadScientist/idle/skeleton.json", 1.0f);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return CardColorEnum.MAD_SCIENCE;
    }

    @Override
    public Color getCardRenderColor() {
        return MadScienceMod.BRONZE;
    }

    @Override
    public Color getCardTrailColor() {
        return MadScienceMod.BRONZE;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed; // TODO: customize?
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("POTION_1", MathUtils.random(-0.2f, 0.2f));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "POTION_1";
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    @Override
    public String getSpireHeartText() {
        return SpireHeart.DESCRIPTIONS[8];
    }

    @Override
    public Color getSlashAttackColor() {
        return MadScienceMod.BRONZE;
    }

    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new MadScientist(this.name);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<String>();
        retVal.add(Strike_MadScience.ID);
        retVal.add(Strike_MadScience.ID);
        retVal.add(Strike_MadScience.ID);
        retVal.add(Strike_MadScience.ID);
        retVal.add(Defend_MadScience.ID);
        retVal.add(Defend_MadScience.ID);
        retVal.add(Defend_MadScience.ID);
        retVal.add(Defend_MadScience.ID);
        retVal.add(PoweredStrike.ID);
        retVal.add(HideBehindJunk.ID);
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<String>();
        retVal.add(FuelTank.ID);
        UnlockTracker.markRelicAsSeen(FuelTank.ID);
        return retVal;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new HideBehindJunk();
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0], START_HP, START_HP, MAX_ORBS, START_GOLD, CARD_DRAW, this,
                getStartingRelics(), getStartingDeck(), false);
    }
}
