package madsciencemod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import madsciencemod.cards.Postpone;

public class HandToTopOfDrawPileAction extends AbstractGameAction {
    private AbstractPlayer p = AbstractDungeon.player;
    private boolean upgrade;
    private int minAmount, maxAmount;

    public HandToTopOfDrawPileAction(int minAmount, int maxAmount, boolean upgrade) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.upgrade = upgrade;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
                return;
            }
            if (this.p.hand.size() == 1 && minAmount >= 1) {
                AbstractCard c = this.p.hand.getTopCard();
                if (upgrade && c.canUpgrade()) {
                    c.upgrade();
                }
                this.p.hand.moveToDeck(c, false);
                AbstractDungeon.player.hand.refreshHandLayout();
                this.isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(Postpone.EXTENDED_DESCRIPTION[0], this.maxAmount, this.minAmount < this.maxAmount, this.minAmount == 0);
            this.tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                if (upgrade && c.canUpgrade()) {
                    c.upgrade();
                }
                this.p.hand.moveToDeck(c, false);
            }
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        this.tickDuration();
    }
}

