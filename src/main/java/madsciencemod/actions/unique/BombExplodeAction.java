package madsciencemod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BombExplodeAction extends AbstractGameAction {
    private static final float DURATION = 0.33f;

    public BombExplodeAction(AbstractCreature target, AbstractCreature source, int amount) {
        this.setValues(target, source, amount);
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = DURATION;
    }

    @Override
    public void update() {
        if (this.duration == DURATION) {
            AbstractDungeon.actionManager.addToTop(new DamageAction(target, new DamageInfo(source, this.amount, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
            this.isDone = true;
        }
        this.tickDuration();
    }
}

