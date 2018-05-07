package inventormod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EmergencyRepairAction extends AbstractGameAction {
    public EmergencyRepairAction() {
        this.source = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            int diff = GameActionManager.playerHpLastTurn - AbstractDungeon.player.currentHealth;
            if (diff > 0) {
                AbstractDungeon.actionManager.addToTop(new HealAction(this.source, this.source, diff));
            }
            this.isDone = true;
        }
        this.tickDuration();
    }
}

