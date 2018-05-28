package madsciencemod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class DrawPileToHandAction extends AbstractGameAction {
    private AbstractCard card;
    private boolean triggerDraw;

    public DrawPileToHandAction(AbstractCard card, boolean triggerDraw) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.card = card;
        this.triggerDraw = triggerDraw;
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
                if (triggerDraw) {
                    // See AbstractPlayer.draw
                    int newCost;
                    if (AbstractDungeon.player.hasPower("Confusion") && this.card.cost > -1 && this.card.color != AbstractCard.CardColor.CURSE && this.card.type != AbstractCard.CardType.STATUS && this.card.cost != (newCost = AbstractDungeon.cardRandomRng.random(3))) {
                        this.card.costForTurn = this.card.cost = newCost;
                        this.card.isCostModified = true;
                    }
                    this.card.triggerWhenDrawn();
                    if (AbstractDungeon.player.hasPower("Corruption") && this.card.type == AbstractCard.CardType.SKILL) {
                        this.card.setCostForTurn(-9);
                    }
                    for (AbstractRelic r : AbstractDungeon.player.relics) {
                        r.onCardDraw(this.card);
                    }
                }
            }
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.glowCheck();
        }
        this.tickDuration();
        this.isDone = true;
    }
}
