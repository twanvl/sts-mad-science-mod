package madsciencemod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import madsciencemod.powers.FuelPower;

public class SpendFuelAction extends AbstractGameAction {
    private int amount;
    Runnable callback;

    public SpendFuelAction(int amount, Runnable callback) {
        this.duration = 0;
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = AbstractDungeon.player;
        this.amount = amount;
        this.callback = callback;
    }

    @Override
    public void update() {
        if (this.isDone) return;
        this.isDone = true;
        if (FuelPower.currentAmount() >= amount) {
            // Note: We could use ReducePowerAction, but that has a slower animation, and is not immediate.
            // Instead, just do the reduction ourselves
            FuelPower.spendFuel(this.amount);
            callback.run();
        }
    }
}

