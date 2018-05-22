package inventormod.actions.common;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.ui.buttons.SingingBowlButton;
import com.megacrit.cardcrawl.ui.buttons.SkipCardButton;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import basemod.ReflectionHacks;
import inventormod.cards.AbstractTrinket;
import inventormod.cards.DecorativeTrinket;
import inventormod.cards.ExplosiveTrinket;
import inventormod.cards.FlamableTrinket;
import inventormod.cards.FoamTrinket;
import inventormod.cards.FoldedTrinket;
import inventormod.cards.RecycledTrinket;
import inventormod.cards.ShinyTrinket;
import inventormod.cards.SpikyTrinket;
import inventormod.relics.PolishingWheel;

public class ShuffleTrinketAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(ShuffleTrinketAction.class.getName());

    private static final float startingDuration = 0.5f;
    private boolean random;
    private static final boolean ALLOW_DUPLICATES = false;
    private static final boolean CHOOSE_FROM_ALL = false;
    private boolean cardOffset;
    private ArrayList<AbstractCard> cardsToShuffle = new ArrayList<>();

    public ShuffleTrinketAction(int amount, boolean random, boolean cardOffset) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = startingDuration;
        this.amount = amount;
        this.random = random;
        this.cardOffset = cardOffset;
    }

    @Override
    public void update() {
        if (this.duration == startingDuration) {
            if (random) {
                // random without replacement
                while(cardsToShuffle.size() < amount) {
                    int tries = 0;
                    AbstractCard card = getRandomTrinket();
                    if (ALLOW_DUPLICATES || !cardsToShuffle.contains(card) || tries++ > 10) {
                        cardsToShuffle.add(card);
                    }
                }
                shuffleTrinkets();
            } else if (CHOOSE_FROM_ALL) {
                // Show a screen to select a number of cards
                if (amount > 0) {
                    String message = amount == 1 ? AbstractTrinket.EXTENDED_DESCRIPTION[0] : AbstractTrinket.EXTENDED_DESCRIPTION[1] + amount + AbstractTrinket.EXTENDED_DESCRIPTION[2];
                    AbstractDungeon.gridSelectScreen.open(allTrinketsToUse(), amount, message, false, false, true, false);
                    amount = 0;
                } else if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
                    cardsToShuffle = AbstractDungeon.gridSelectScreen.selectedCards;
                    shuffleTrinkets();
                    AbstractDungeon.gridSelectScreen.selectedCards.clear();
                }
            } else {
                // Show a screen to select a number of cards
                if (AbstractDungeon.cardRewardScreen.codexCard != null) {
                    AbstractCard c = AbstractDungeon.cardRewardScreen.codexCard.makeStatEquivalentCopy();
                    cardsToShuffle.add(c);
                    AbstractDungeon.cardRewardScreen.codexCard = null;
                }
                if (amount > 0) {
                    amount--;
                    openTrinketRewardsScreen();
                    return; // don't tickDuration, so we can open the screen again
                } else {
                    shuffleTrinkets();
                }
            }
        }
        this.tickDuration();
    }

    private static void openTrinketRewardsScreen() {
        // Based on CardRewardScreen.codexOpen
        CardRewardScreen crs = AbstractDungeon.cardRewardScreen;
        crs.rItem = null;
        ReflectionHacks.setPrivate(crs, CardRewardScreen.class, "codex", true);
        ReflectionHacks.setPrivate(crs, CardRewardScreen.class, "draft", false);
        crs.codexCard = null;
        ((SingingBowlButton)ReflectionHacks.getPrivate(crs, CardRewardScreen.class, "bowlButton")).hide();
        ((SkipCardButton)   ReflectionHacks.getPrivate(crs, CardRewardScreen.class, "skipButton")).show();
        crs.onCardSelect = true;
        AbstractDungeon.topPanel.unhoverHitboxes();
        ArrayList<AbstractCard> derp = new ArrayList<AbstractCard>();
        while (derp.size() != 3) {
            AbstractCard card = getRandomTrinket();
            if (!derp.contains(card)) {
                derp.add(card);
            }
        }
        crs.rewardGroup = derp;
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.CARD_REWARD;
        AbstractDungeon.dynamicBanner.appear(AbstractTrinket.EXTENDED_DESCRIPTION[0]);
        AbstractDungeon.overlayMenu.showBlackScreen();
        final float CARD_TARGET_Y = (float)Settings.HEIGHT * 0.45f;
        try {
            Method method = CardRewardScreen.class.getDeclaredMethod("placeCards", float.class, float.class);
            method.setAccessible(true);
            method.invoke(crs, (float)Settings.WIDTH / 2.0f, CARD_TARGET_Y);
         } catch (Exception ex) {
            logger.error("Exception occured when calling placeCards", ex);
         }
        for (AbstractCard c : derp) {
            UnlockTracker.markCardAsSeen(c.cardID);
        }
    }

    private void shuffleTrinkets() {
        // see MakeTempCardInDrawPileAction
        boolean randomSpot = true;
        for (AbstractCard c : cardsToShuffle) {
            c.unhover();
            if (cardsToShuffle.size() < 6) {
                AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, (float)Settings.WIDTH / 2.0f, (float)Settings.HEIGHT / 2.0f, randomSpot, cardOffset));
            } else {
                AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, randomSpot));
            }
        }
        cardsToShuffle.clear();
    }

    private static CardGroup allTrinkets;
    private static CardGroup allUpgradedTrinkets;
    {
        allTrinkets = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        allTrinkets.addToTop(new DecorativeTrinket());
        allTrinkets.addToTop(new ExplosiveTrinket());
        allTrinkets.addToTop(new FoamTrinket());
        allTrinkets.addToTop(new FoldedTrinket());
        allTrinkets.addToTop(new FlamableTrinket());
        allTrinkets.addToTop(new RecycledTrinket());
        allTrinkets.addToTop(new SpikyTrinket());
        allTrinkets.addToTop(new ShinyTrinket());
        // Note: no TastyTrinket
        allTrinkets.sortAlphabetically(false);
        // Upgraded
        allUpgradedTrinkets = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : allTrinkets.group) {
            AbstractCard copy = c.makeCopy();
            copy.upgrade();
            allUpgradedTrinkets.addToTop(copy);
        }
    }

    private static CardGroup allTrinketsToUse() {
        if (AbstractDungeon.player.hasRelic(PolishingWheel.ID)) {
            return allUpgradedTrinkets;
        } else {
            return allTrinkets;
        }
    }

    private static AbstractCard getRandomTrinket() {
        return allTrinketsToUse().getRandomCard(false);
    }
}
