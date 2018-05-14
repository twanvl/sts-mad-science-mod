package inventormod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import inventormod.cards.AbstractTrinket;

public class AllTrinketsToHandAction extends AbstractGameAction {
    private AbstractPlayer p = AbstractDungeon.player;

    public AllTrinketsToHandAction() {
        this.setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        for (AbstractCard card : this.p.drawPile.group) {
            if (card instanceof AbstractTrinket) {
                AbstractDungeon.actionManager.addToBottom(new DrawPileToHandAction(card));
            }
        }
        this.tickDuration();
        this.isDone = true;
    }
}

