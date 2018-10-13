package madsciencemod;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.localization.Keyword;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;

import madsciencemod.patches.PlayerClassEnum;
import madsciencemod.powers.FuelPower;
import madsciencemod.patches.CardColorEnum;
import madsciencemod.characters.*;
import madsciencemod.cards.*;
import madsciencemod.relics.*;
import madsciencemod.potions.*;

@SpireInitializer
public class MadScienceMod implements
        PostInitializeSubscriber,
        EditRelicsSubscriber,
        EditCharactersSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCardsSubscriber,
        PostBattleSubscriber {
    public static final String MODNAME = "MadScienceMod";
    public static final String AUTHOR = "twanvl";
    public static final String DESCRIPTION = "v0.1.9\nAdds a new character: the Mad Scientist.";

    // card trail effect
    private static final Color BRONZE = new Color(0.7f, 0.6f, 0.1f, 1.0f);

    public static final Logger logger = LogManager.getLogger(MadScienceMod.class.getName());

    public static String cardImage(String id) {
        return "img/cards/" + id + ".png";
    }
    public static String relicImage(String id) {
        return "img/relics/" + id + ".png";
    }
    public static String relicOutline(String id) {
        return "img/relics/outline/" + id + ".png";
    }
    public static String powerImage(String id) {
        return "img/powers/32/" + id + ".png";
    }

    public MadScienceMod() {
        BaseMod.subscribe(this);
        receiveEditColors();
    }

    public static void initialize() {
        new MadScienceMod();
    }

    @Override
    public void receivePostInitialize() {
        logger.info("initialize mod badge");
        // Mod badge
        Texture badgeTexture = new Texture("img/MadScienceModBadge.png");
        ModPanel settingsPanel = new ModPanel();
        settingsPanel.addUIElement(new ModLabel("This mod does not have any settings.", 400.0f, 700.0f, settingsPanel, (me) -> {}));
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
        logger.info("done with mod badge");
    }

    public void receiveEditColors() {
        logger.info("begin editing colors");
        BaseMod.addColor(
            CardColorEnum.MAD_SCIENCE,
            BRONZE, BRONZE, BRONZE, BRONZE, BRONZE, BRONZE, BRONZE,
            "img/cardui/512/bg_attack_mad_science.png",
            "img/cardui/512/bg_skill_mad_science.png",
            "img/cardui/512/bg_power_mad_science.png",
            "img/cardui/512/card_mad_science_orb.png",
            "img/cardui/1024/bg_attack_mad_science.png",
            "img/cardui/1024/bg_skill_mad_science.png",
            "img/cardui/1024/bg_power_mad_science.png",
            "img/cardui/1024/card_mad_science_orb.png",
            "img/cardui/small_mad_science_orb.png");
        logger.info("done editing colors");
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("begin editing characters");
        BaseMod.loadCustomStrings(CharacterStrings.class, loadJson("localization/eng/madscience-characters.json"));
        BaseMod.addCharacter(
            MadScientist.class,
            MadScientist.NAMES[1],
            "MadScientist class string",
            CardColorEnum.MAD_SCIENCE,
            MadScientist.NAMES[0],
            "img/charSelect/MadScientistButton.png",
            "img/charSelect/MadScientistPortrait.jpg",
            PlayerClassEnum.MAD_SCIENTIST);
        logger.info("done editing characters");
        receiveEditPotions();
    }

    @Override
    public void receiveEditRelics() {
        logger.info("begin editing relics");
        BaseMod.addRelicToCustomPool(new madsciencemod.relics.ExhaustVents(), CardColorEnum.MAD_SCIENCE);
        BaseMod.addRelicToCustomPool(new FuelTank(), CardColorEnum.MAD_SCIENCE);
        BaseMod.addRelicToCustomPool(new Funnel(), CardColorEnum.MAD_SCIENCE);
        BaseMod.addRelic(new InfiniteJournal(), RelicType.SHARED);
        BaseMod.addRelicToCustomPool(new PolishingWheel(), CardColorEnum.MAD_SCIENCE);
        BaseMod.addRelicToCustomPool(new madsciencemod.relics.PortablePumpjack(), CardColorEnum.MAD_SCIENCE);
        BaseMod.addRelicToCustomPool(new SignalFlag(), CardColorEnum.MAD_SCIENCE);
        BaseMod.addRelicToCustomPool(new DoomsdayDevice(), CardColorEnum.MAD_SCIENCE);
        logger.info("end editing relics");
    }

    @Override
    public void receiveEditCards() {
        logger.info("begin editing cards");
        try {
            BaseMod.addCard(new AcidSpray());
            BaseMod.addCard(new AntimatterBomb());
            BaseMod.addCard(new ArtilleryShot());
            BaseMod.addCard(new Assemble());
            BaseMod.addCard(new AssemblyLine());
            BaseMod.addCard(new Automate());
            BaseMod.addCard(new AutoStrike());
            BaseMod.addCard(new BandageBot());
            BaseMod.addCard(new Bayonet());
            BaseMod.addCard(new Bombardment());
            BaseMod.addCard(new Booster());
            BaseMod.addCard(new BoxOfScraps());
            BaseMod.addCard(new Browse());
            BaseMod.addCard(new CaptureMomentum());
            //BaseMod.addCard(new DEPRECATED_ChargeUp());
            BaseMod.addCard(new ClockworkAssistant());
            BaseMod.addCard(new CloneVats());
            //BaseMod.addCard(new DEPRECATED_ComputeTrajectory());
            BaseMod.addCard(new CondenseVapors());
            //BaseMod.addCard(new DataRecorder());
            BaseMod.addCard(new Defend_MadScience());
            BaseMod.addCard(new Detonate());
            //BaseMod.addCard(new DiscountBin());
            //BaseMod.addCard(new DEPRECATED_EmergencyRepair());
            BaseMod.addCard(new EnergyBarrier());
            //BaseMod.addCard(new madsciencemod.cards.ExhaustVents());
            BaseMod.addCard(new ExploreOptions());
            BaseMod.addCard(new ExtractOil());
            BaseMod.addCard(new FlammableWall());
            BaseMod.addCard(new FlameCannon());
            BaseMod.addCard(new FlameThrower());
            BaseMod.addCard(new FlexibleSpear());
            BaseMod.addCard(new FragmentationGrenade());
            BaseMod.addCard(new FumeBlast());
            BaseMod.addCard(new FumeHood());
            //BaseMod.addCard(new DEPRECATED_GarbageDisposalCannon());
            BaseMod.addCard(new GatherParts());
            BaseMod.addCard(new GattlingGun());
            BaseMod.addCard(new Generator());
            BaseMod.addCard(new GlassCannon());
            BaseMod.addCard(new GoldenGun());
            //BaseMod.addCard(new Grind());
            BaseMod.addCard(new HideBehindJunk());
            BaseMod.addCard(new ImposingConstruct());
            BaseMod.addCard(new Inflate());
            BaseMod.addCard(new Jetpack());
            BaseMod.addCard(new JunkLaser());
            BaseMod.addCard(new LabAccident());
            BaseMod.addCard(new LightOnFire());
            BaseMod.addCard(new Lightbulb());
            BaseMod.addCard(new Liquify());
            BaseMod.addCard(new MakeOrBreak());
            BaseMod.addCard(new MechanicalClaw());
            BaseMod.addCard(new MechanicalSnecko());
            BaseMod.addCard(new Mechanize());
            BaseMod.addCard(new MinatureWeapons());
            BaseMod.addCard(new MindControlBeam());
            BaseMod.addCard(new NowOrNever());
            BaseMod.addCard(new OilDrum());
            BaseMod.addCard(new OmniHammer());
            BaseMod.addCard(new ParticleBeam());
            //BaseMod.addCard(new PneumaticHammer());
            //BaseMod.addCard(new madsciencemod.cards.PortablePumpjack());
            BaseMod.addCard(new Poke());
            BaseMod.addCard(new Postpone());
            BaseMod.addCard(new PowerArmor());
            BaseMod.addCard(new PoweredStrike());
            BaseMod.addCard(new Probe());
            BaseMod.addCard(new RadioactiveSword());
            BaseMod.addCard(new Reconstitute());
            BaseMod.addCard(new RecyclingStation());
            BaseMod.addCard(new Redesign());
            BaseMod.addCard(new Retarget());
            BaseMod.addCard(new Refine());
            BaseMod.addCard(new Refuel());
            //BaseMod.addCard(new RocketJump());
            BaseMod.addCard(new SelfDestruct());
            BaseMod.addCard(new ShrinkRay());
            BaseMod.addCard(new SoftenUp());
            BaseMod.addCard(new Sprocket());
            //BaseMod.addCard(new StickSharpener());
            BaseMod.addCard(new Strafe());
            BaseMod.addCard(new Strike_MadScience());
            //BaseMod.addCard(new DEPRECATED_TargetingLaser());
            //BaseMod.addCard(new ThrowScraps());
            BaseMod.addCard(new TimeDilationField());
            BaseMod.addCard(new Tinker());
            BaseMod.addCard(new Tune());
            BaseMod.addCard(new Virus());
            BaseMod.addCard(new Weaponize());

            // Trinkets
            BaseMod.addCard(new DecorativeTrinket());
            BaseMod.addCard(new ExplosiveTrinket());
            BaseMod.addCard(new FlammableTrinket());
            BaseMod.addCard(new FoldedTrinket());
            BaseMod.addCard(new FoamTrinket());
            BaseMod.addCard(new RecycledTrinket());
            BaseMod.addCard(new ShinyTrinket());
            BaseMod.addCard(new SpikyTrinket());
            BaseMod.addCard(new TastyTrinket());
            BaseMod.addCard(new FusedTrinket(false));
        } catch (Exception e) {
            logger.error("Error while adding cards",e);
        }
        logger.info("done editing cards");
    }

    public void receiveEditPotions() {
        logger.info("begin editing potions");
        BaseMod.addPotion(FuelPotion.class, new Color(0.3f,0.3f,0.3f,1.0f), new Color(0.1f,0.1f,0.1f,1.0f), new Color(0.5f,0.5f,0.5f,1.0f), FuelPotion.POTION_ID, PlayerClassEnum.MAD_SCIENTIST);
        logger.info("end editing potions");
    }

    @Override
    public void receiveEditKeywords() {
        logger.info("begin editing keywords");
        // Note: KeywordStrings is a horrible hardcoded class, we can't use it
        // use a custom class instead
        Type typeToken = new TypeToken<Map<String, Keyword>>(){}.getType();
        Gson gson = new Gson();
        String strings = loadJson("localization/eng/madscience-keywords.json");
        @SuppressWarnings("unchecked")
        Map<String,Keyword> keywords = (Map<String,Keyword>)gson.fromJson(strings, typeToken);
        for (Keyword kw : keywords.values()) {
            BaseMod.addKeyword(kw.NAMES, kw.DESCRIPTION);
        }
        logger.info("done editing keywords");
    }

    @Override
    public void receiveEditStrings() {
        logger.info("begin editing strings");
        // Note: it seems that naming the files localization/eng/relics.json crashes slay the spire on startup
        BaseMod.loadCustomStrings(RelicStrings.class, loadJson("localization/eng/madscience-relics.json"));
        BaseMod.loadCustomStrings(CardStrings.class, loadJson("localization/eng/madscience-cards.json"));
        BaseMod.loadCustomStrings(PowerStrings.class, loadJson("localization/eng/madscience-powers.json"));
        BaseMod.loadCustomStrings(PotionStrings.class, loadJson("localization/eng/madscience-potions.json"));
        logger.info("done editing strings");
    }
    private static String loadJson(String jsonPath) {
        return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
    }

    @Override
    public void receivePostBattle(AbstractRoom arg0) {
        FuelPower.atBattleEnd();
    }
}
