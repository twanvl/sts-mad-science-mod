package madsciencemod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RandomizeCostForTurnAction extends AbstractGameAction {
    private float startingDuration;

    public RandomizeCostForTurnAction() {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = this.startingDuration = Settings.ACTION_DUR_XFAST;
    }

    @Override
    public void update() {
        if (this.duration == this.startingDuration) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.cost > -1 && c.color != AbstractCard.CardColor.CURSE && c.type != AbstractCard.CardType.STATUS) {
                    // Note: Don't use setCostForTurn, since that doesn't change cards that cost 0
                    c.costForTurn = AbstractDungeon.cardRandomRng.random(3);
                    if (c.costForTurn != c.cost) {
                        c.isCostModifiedForTurn = true;
                    }
                }
            }
        }
        this.tickDuration();
    }
}
