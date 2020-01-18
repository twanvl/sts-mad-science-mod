package madsciencemod;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {
    public static final Logger logger = LogManager.getLogger(Utils.class.getName());

    public static void openCardRewardsScreen(ArrayList<AbstractCard> cards, boolean allowSkip) {
        AbstractDungeon.cardRewardScreen.customCombatOpen(cards, CardRewardScreen.TEXT[1], allowSkip);
    }

}