package madsciencemod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import madsciencemod.cards.AbstractMadScienceCard;

public class MechanizeAction extends AbstractGameAction {
    private float startingDuration;

    public MechanizeAction() {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = this.startingDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == this.startingDuration) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c instanceof AbstractMadScienceCard) {
                    AbstractMadScienceCard ci = (AbstractMadScienceCard)c;
                    ci.costsFuelThisTurn = true;
                }
                // TODO: somehow set fuel cost of non-mod cards
            }
        }
        this.tickDuration();
    }
}
