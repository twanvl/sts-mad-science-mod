package madsciencemod.actions.common;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

import madsciencemod.Utils;
import madsciencemod.cards.AbstractTrinket;
import madsciencemod.cards.DecorativeTrinket;
import madsciencemod.cards.ExplosiveTrinket;
import madsciencemod.cards.FlammableTrinket;
import madsciencemod.cards.FoamTrinket;
import madsciencemod.cards.FoldedTrinket;
import madsciencemod.cards.RecycledTrinket;
import madsciencemod.cards.ShinyTrinket;
import madsciencemod.cards.SpikyTrinket;
import madsciencemod.relics.PolishingWheel;

public class ShuffleTrinketAction extends AbstractGameAction {
    private static final float startingDuration = 0.5f;
    private boolean random;
    private static final boolean ALLOW_DUPLICATES = false;
    private static final boolean CHOOSE_FROM_ALL = false;
    private boolean cardOffset;
    private boolean upgraded;
    private ArrayList<AbstractCard> cardsToShuffle = new ArrayList<>();

    public ShuffleTrinketAction(int amount, boolean random, boolean cardOffset) {
        this(amount, random, cardOffset, false);
    }

    public ShuffleTrinketAction(int amount, boolean random, boolean cardOffset, boolean upgraded) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = startingDuration;
        this.amount = amount;
        this.random = random;
        this.cardOffset = cardOffset;
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        if (this.duration == startingDuration) {
            if (random) {
                // random without replacement
                cardsToShuffle = getRandomTrinkets(amount, ALLOW_DUPLICATES);
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
                    Utils.openCardRewardsScreen(getRandomTrinkets(3,false), true);
                    return; // don't tickDuration, so we can open the screen again
                } else {
                    shuffleTrinkets();
                }
            }
        }
        this.tickDuration();
    }

    private void shuffleTrinkets() {
        // see MakeTempCardInDrawPileAction
        boolean randomSpot = true;
        for (AbstractCard c : cardsToShuffle) {
            c.unhover();
            if (cardsToShuffle.size() < 6) {
                AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, (float)Settings.WIDTH / 2.0f, (float)Settings.HEIGHT / 2.0f, randomSpot, cardOffset));
            } else {
                AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, randomSpot, false));
            }
        }
        cardsToShuffle.clear();
    }

    private static CardGroup allTrinkets;
    private static CardGroup allUpgradedTrinkets;
    static {
        allTrinkets = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        allTrinkets.addToTop(new DecorativeTrinket());
        allTrinkets.addToTop(new ExplosiveTrinket());
        allTrinkets.addToTop(new FoamTrinket());
        allTrinkets.addToTop(new FoldedTrinket());
        allTrinkets.addToTop(new FlammableTrinket());
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

    private static CardGroup allTrinketsToUse(boolean upgraded) {
        if (upgraded || AbstractDungeon.player.hasRelic(PolishingWheel.ID)) {
            return allUpgradedTrinkets;
        } else {
            return allTrinkets;
        }
    }

    private CardGroup allTrinketsToUse() {
        return allTrinketsToUse(this.upgraded);
    }

    public static AbstractCard getRandomTrinket(boolean upgraded) {
        return allTrinketsToUse(upgraded).getRandomCard(false);
    }

    private AbstractCard getRandomTrinket() {
        return allTrinketsToUse().getRandomCard(false);
    }

    private ArrayList<AbstractCard> getRandomTrinkets(int amount, boolean allowDuplicates) {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        while(cards.size() < amount) {
            int tries = 0;
            AbstractCard card = getRandomTrinket();
            if (allowDuplicates || !cards.contains(card) || tries++ > 10) {
                cards.add(card);
            }
        }
        return cards;
    }
}
