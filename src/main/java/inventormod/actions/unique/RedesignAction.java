package inventormod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RedesignAction extends AbstractGameAction {
    private float startingDuration;
    private int draw_amount;

    public RedesignAction(int draw_amount) {
        this.target = AbstractDungeon.player;
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.duration = this.startingDuration = Settings.ACTION_DUR_FAST;
        this.draw_amount = draw_amount;
    }

    @Override
    public void update() {
        if (this.duration == this.startingDuration) {
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(this.target, draw_amount));
            int count = AbstractDungeon.player.hand.size();
            AbstractDungeon.actionManager.addToTop(new DiscardAction(this.target, this.target, count, true));
            AbstractDungeon.actionManager.addToTop(new ArmamentsAction(true));
        }
        tickDuration();
    }
}
