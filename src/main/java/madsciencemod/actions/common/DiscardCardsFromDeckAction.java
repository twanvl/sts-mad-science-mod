package madsciencemod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import madsciencemod.cards.DEPRECATED_ComputeTrajectory;

public class DiscardCardsFromDeckAction extends AbstractGameAction {
    private float startingDuration;

    public DiscardCardsFromDeckAction() {
        this(1000000000);
    }
    public DiscardCardsFromDeckAction(int amount) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = this.startingDuration = Settings.ACTION_DUR_MED;
    }

    @Override
    public void update() {
        if (this.duration == this.startingDuration) {
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                tmp.addToRandomSpot(c);
            }
            if (tmp.size() == 0) {
                this.isDone = true;
                return;
            }
            AbstractDungeon.gridSelectScreen.open(tmp, Math.min(this.amount,tmp.size()), DEPRECATED_ComputeTrajectory.EXTENDED_DESCRIPTION[0], false, false, false, true);
            this.tickDuration();
            return;
        }
        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                c.unhover();
                AbstractDungeon.player.drawPile.moveToDiscardPile(c);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
        }
        this.tickDuration();
    }
}