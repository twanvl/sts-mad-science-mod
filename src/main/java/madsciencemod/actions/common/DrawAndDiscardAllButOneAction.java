package madsciencemod.actions.common;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class DrawAndDiscardAllButOneAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
    public static final String[] TEXT = DrawAndDiscardAllButOneAction.uiStrings.TEXT;
    private float startingDuration;
    private CardGroup actualHand;
    private ArrayList<AbstractCard> drawnCards;

    public DrawAndDiscardAllButOneAction(int amount) {
        this(amount, new ArrayList<>());
    }
    private DrawAndDiscardAllButOneAction(int amount, ArrayList<AbstractCard> drawnCards) {
        this.drawnCards = drawnCards;
        this.amount = amount;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = this.startingDuration = Settings.FAST_MODE ? Settings.ACTION_DUR_XFAST : Settings.ACTION_DUR_FASTER;
    }

    @Override
    public void update() {
        // Draw cards, keep track of drawn cards, when enough are drawn
        if (SoulGroup.isActive()) {
            return;
        }
        int deckSize = AbstractDungeon.player.drawPile.size();
        int discardSize = AbstractDungeon.player.discardPile.size();
        if (deckSize == 0 && discardSize > 0 && drawnCards.size() < amount) {
            // continue after shuffle
            AbstractDungeon.actionManager.addToTop(new DrawAndDiscardAllButOneAction(amount, drawnCards));
            AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
            this.isDone = true;
            return;
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0f) {
            if (drawnCards.size() >= amount || AbstractDungeon.player.drawPile.isEmpty() || AbstractDungeon.player.hand.size() == 10) {
                // found a 0 cost card, or no more cards to draw
                if (actualHand == null) {
                    if (drawnCards.size() <= 1) {
                        // no discard
                        this.isDone = true;
                        return;
                    }
                    // show HandCardSelectScreen for the drawn cards
                    actualHand = AbstractDungeon.player.hand;
                    AbstractDungeon.player.hand = new CardGroup(CardGroupType.HAND);
                    AbstractDungeon.player.hand.group = drawnCards;
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], drawnCards.size() - 1, false);
                    AbstractDungeon.player.hand.applyPowers();
                } else {
                    if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                        AbstractDungeon.player.hand = actualHand;
                        for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                            AbstractDungeon.player.hand.moveToDiscardPile(c);
                            c.triggerOnManualDiscard();
                            GameActionManager.incrementDiscard(false);
                        }
                        AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                    }
                    this.isDone = true;
                }
            } else {
                drawnCards.add(AbstractDungeon.player.drawPile.getTopCard());
                AbstractDungeon.player.draw();
                AbstractDungeon.player.hand.refreshHandLayout();
                // draw again, or check if done
                this.duration = startingDuration;
            }
        }
    }
}