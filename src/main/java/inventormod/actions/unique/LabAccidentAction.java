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

    public LabAccidentAction(boolean upgraded) {
        this.upgraded = upgraded;
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.duration = this.startingDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == this.startingDuration) {
            int i;
            int count = AbstractDungeon.player.hand.size();
            for (i = 0; i < count; ++i) {
                AbstractCard c = AbstractDungeon.returnTrulyRandomCard().makeCopy();
                if (this.upgraded) {
                    c.upgrade();
                }
                c.setCostForTurn(-9);
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, 1));
            }
            for (i = 0; i < count; ++i) {
                AbstractDungeon.actionManager.addToTop(new ExhaustAction(AbstractDungeon.player, AbstractDungeon.player, 1, true, true));
            }
        }
        this.tickDuration();
    }
}
