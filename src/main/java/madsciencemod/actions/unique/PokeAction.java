package madsciencemod.actions.unique;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.BaseMod;

public class PokeAction extends AbstractGameAction {
    private float startingDuration;
    private ArrayList<AbstractCard> drawnCards;

    public PokeAction() {
        this(new ArrayList<>());
    }
    private PokeAction(ArrayList<AbstractCard> drawnCards) {
        this.drawnCards = drawnCards;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = this.startingDuration = Settings.FAST_MODE ? Settings.ACTION_DUR_XFAST : Settings.ACTION_DUR_FASTER;
    }

    @Override
    public void update() {
        // Draw a card, keep track of drawn cards
        if (SoulGroup.isActive()) {
            return;
        }
        boolean found = !drawnCards.isEmpty() && drawnCards.get(drawnCards.size()-1).cost == 0;
        int deckSize = AbstractDungeon.player.drawPile.size();
        int discardSize = AbstractDungeon.player.discardPile.size();
        if (deckSize == 0 && discardSize > 0 && !found) {
            // continue after shuffle
            AbstractDungeon.actionManager.addToTop(new PokeAction(drawnCards));
            AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
            this.isDone = true;
            return;
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0f) {
            if (found || AbstractDungeon.player.drawPile.isEmpty() || AbstractDungeon.player.hand.size() == BaseMod.MAX_HAND_SIZE) {
                // found a 0 cost card, or no more cards to draw
                if (found) {
                    drawnCards.remove(drawnCards.size()-1);
                } else if (AbstractDungeon.player.hand.size() == 10) {
                    AbstractDungeon.player.createHandIsFullDialog();
                }
                for (AbstractCard c : drawnCards) {
                    AbstractDungeon.player.hand.moveToDiscardPile(c);
                    c.triggerOnManualDiscard();
                    GameActionManager.incrementDiscard(false);
                }
                this.isDone = true;
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
