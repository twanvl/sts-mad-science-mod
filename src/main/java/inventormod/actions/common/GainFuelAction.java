package inventormod.actions.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import inventormod.powers.FuelPower;
import inventormod.relics.Funnel;

public class GainFuelAction extends ApplyPowerAction {
    public GainFuelAction(int amount) {
        this(amount, false, true);
    }

    public GainFuelAction(int amount, boolean isFast, boolean duringCombat) {
        super(AbstractDungeon.player, AbstractDungeon.player, new FuelPower(AbstractDungeon.player, adjustAmount(amount, duringCombat)), adjustAmount(amount, duringCombat), isFast);
    }

    private static int adjustAmount(int amount, boolean duringCombat) {
        if (AbstractDungeon.player.hasRelic(Funnel.ID)) {
            AbstractDungeon.player.getRelic(Funnel.ID).flash();
            ++amount;
        }
        return amount;
    }
}
