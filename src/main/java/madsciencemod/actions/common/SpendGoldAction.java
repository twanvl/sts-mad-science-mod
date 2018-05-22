package madsciencemod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SpendGoldAction extends AbstractGameAction {
    private int amount;
    Runnable callback;

    public SpendGoldAction(int amount, Runnable callback) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.amount = amount;
        this.callback = callback;
    }

    @Override
    public void update() {
        if (this.isDone) return;
        this.isDone = true;
        if (AbstractDungeon.player.gold >= amount) {
            AbstractDungeon.player.loseGold(amount);
            callback.run();
        }
    }
}
