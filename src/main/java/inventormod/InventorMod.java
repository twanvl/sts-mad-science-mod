package inventormod;

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
import com.megacrit.cardcrawl.localization.Keyword;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import basemod.BaseMod;
import basemod.ModPanel;
import basemod.interfaces.*;

import inventormod.patches.PlayerClassEnum;
import inventormod.patches.CardColorEnum;
import inventormod.characters.*;
import inventormod.cards.*;
import inventormod.relics.*;

@SpireInitializer
public class InventorMod implements
        PostInitializeSubscriber,
        EditRelicsSubscriber,
        EditCharactersSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCardsSubscriber {
    public static final String MODNAME = "Inventor Character";
    public static final String AUTHOR = "twanvl";
    public static final String DESCRIPTION = "v0.1.0\nAdds a new character: The Inventor.";

    // card trail effect
    private static final Color BRONZE = new Color(0.1f, 0.1f, 0.7f, 1.0f);

	public static final Logger logger = LogManager.getLogger(InventorMod.class.getName());

    public InventorMod() {
        BaseMod.subscribeToPostInitialize(this);
        BaseMod.subscribeToEditStrings(this);
        BaseMod.subscribeToEditCharacters(this);
        BaseMod.subscribeToEditRelics(this);
        BaseMod.subscribeToEditKeywords(this);
        BaseMod.subscribeToEditCards(this);
        receiveEditColors();
    }

    public static void initialize() {
        new InventorMod();
    }

    @Override
    public void receivePostInitialize() {
        logger.info("initialize mod badge");
        // Mod badge
        Texture badgeTexture = new Texture("img/InventorModBadge.png");
        ModPanel settingsPanel = new ModPanel();
        settingsPanel.addLabel("This mod does not have any settings.", 400.0f, 700.0f, (me) -> {});
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
        logger.info("done with mod badge");
    }

    public void receiveEditColors() {
        logger.info("begin editing colors");
        BaseMod.addColor(
            CardColorEnum.BRONZE.toString(),
            BRONZE, BRONZE, BRONZE, BRONZE, BRONZE, BRONZE, BRONZE,
            "img/cardui/512/bg_attack_bronze.png",
            "img/cardui/512/bg_skill_bronze.png",
            "img/cardui/512/bg_power_bronze.png",
            "img/cardui/512/card_bronze_orb.png",
            "img/cardui/1024/bg_attack_bronze.png",
            "img/cardui/1024/bg_skill_bronze.png",
            "img/cardui/1024/bg_power_bronze.png",
            "img/cardui/1024/card_bronze_orb.png");
        logger.info("done editing colors");
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("begin editing characters");
        BaseMod.loadCustomStrings(CharacterStrings.class, loadJson("localization/eng/inventor-character.json"));
        logger.info("?" + Inventor.NAMES == null);
        logger.info("?" + Inventor.NAMES.length);
        BaseMod.addCharacter(
            Inventor.class,
            Inventor.NAMES[1],
            "Inventor class string",
            CardColorEnum.BRONZE.toString(),
            Inventor.NAMES[0],
            "img/charSelect/inventorButton.png",
            "img/charSelect/inventorPortrait.jpg",
            PlayerClassEnum.INVENTOR.toString());
        logger.info("done editing characters");
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new FuelTank(), CardColorEnum.BRONZE.toString());
    }
    
    @Override
    public void receiveEditCards() {
        logger.info("begin editing cards");
        BaseMod.addCard(new Strike_Bronze());
        BaseMod.addCard(new Defend_Bronze());
        BaseMod.addCard(new PoweredDefend());
        BaseMod.addCard(new PoweredStrike());
        BaseMod.addCard(new Refuel());
        BaseMod.addCard(new Generator());
        logger.info("done editing cards");
    }

    @Override
    public void receiveEditKeywords() {
        logger.info("begin editing keywords");
        // Note: KeywordStrings is a horrible hardcoded class, we can't use it
        // use a custom class instead
        Type typeToken = new TypeToken<Map<String, Keyword>>(){}.getType();
        Gson gson = new Gson();
        String strings = loadJson("localization/eng/inventor-keywords.json");
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
        BaseMod.loadCustomStrings(RelicStrings.class, loadJson("localization/eng/inventor-relics.json"));
        BaseMod.loadCustomStrings(CardStrings.class, loadJson("localization/eng/inventor-cards.json"));
        BaseMod.loadCustomStrings(PowerStrings.class, loadJson("localization/eng/inventor-powers.json"));
        logger.info("done editing strings");
    }
    private static String loadJson(String jsonPath) {
        return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
    }
}
