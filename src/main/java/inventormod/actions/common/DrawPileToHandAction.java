package inventormod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DrawPileToHandAction extends AbstractGameAction {
    private AbstractCard card;

    public DrawPileToHandAction(AbstractCard card) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.card = card;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.drawPile.contains(this.card) && AbstractDungeon.player.hand.size() < 10) {
                AbstractDungeon.player.hand.addToHand(this.card);
                this.card.unhover();
                this.card.setAngle(0.0f, true);
                this.card.lighten(false);
                this.card.drawScale = 0.12f;
                this.card.targetDrawScale = 0.75f;
                this.card.applyPowers();
                AbstractDungeon.player.drawPile.removeCard(this.card);
            }
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.glowCheck();
        }
        this.tickDuration();
        this.isDone = true;
    }
}
