package madsciencemod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TinkerAction extends AbstractGameAction {
    private boolean upgraded;

    public TinkerAction(boolean upgraded) {
        this.duration = 0.0f;
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.drawPile.isEmpty()) {
            this.isDone = true;
            return;
        }
        AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
        if (card.canUpgrade()) {
            card.upgrade();
            card.superFlash();
        }
        if (this.upgraded) {
            card.setCostForTurn(card.cost-1);
        }
        this.isDone = true;
    }
}

