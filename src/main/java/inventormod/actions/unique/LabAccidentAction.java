package inventormod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class LabAccidentAction extends AbstractGameAction {
    private float startingDuration;
    private boolean upgraded;

    public LabAccidentAction(boolean upgraded, int amount) {
        this.upgraded = upgraded;
        this.amount = amount;
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.duration = this.startingDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == this.startingDuration) {
            for (int i = 0; i < amount; ++i) {
                AbstractCard c = AbstractDungeon.returnTrulyRandomCard().makeCopy();
                if (this.upgraded) {
                    c.upgrade();
                }
                c.setCostForTurn(-9);
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, 1));
            }
            int count = AbstractDungeon.player.hand.size();
            for (int i = 0; i < count; ++i) {
                AbstractDungeon.actionManager.addToTop(new ExhaustAction(AbstractDungeon.player, AbstractDungeon.player, 1, true, true));
            }
        }
        this.tickDuration();
    }
}
