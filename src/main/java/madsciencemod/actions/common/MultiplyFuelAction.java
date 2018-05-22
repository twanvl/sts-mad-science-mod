package madsciencemod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import madsciencemod.powers.FuelPower;

public class MultiplyFuelAction extends AbstractGameAction {
    int multiplier;

    public MultiplyFuelAction(int multiplier) {
        this.duration = 0.5f;
        this.actionType = AbstractGameAction.ActionType.POWER;
        this.target = AbstractDungeon.player;
        this.multiplier = multiplier;
    }

    @Override
    public void update() {
        int amount = FuelPower.currentAmount() * (multiplier - 1);
        if (this.duration == 0.5f && !this.isDone && this.target != null && amount > 0) {
            AbstractDungeon.actionManager.addToBottom(new GainFuelAction(amount));
            this.isDone = true;
        }
        this.tickDuration();
    }
}
