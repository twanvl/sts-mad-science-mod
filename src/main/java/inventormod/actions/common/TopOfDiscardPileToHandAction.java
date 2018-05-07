package inventormod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TopOfDiscardPileToHandAction extends AbstractGameAction {
    private AbstractPlayer p = AbstractDungeon.player;

    public TopOfDiscardPileToHandAction() {
        this.setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        if (this.p.discardPile.size() > 0) {
            AbstractCard card = this.p.discardPile.getTopCard();
            this.p.hand.addToHand(card);
            card.lighten(false);
            this.p.discardPile.removeCard(card);
            this.p.hand.refreshHandLayout();
        }
        this.tickDuration();
        this.isDone = true;
    }
}

