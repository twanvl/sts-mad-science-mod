package inventormod.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import inventormod.actions.common.GainFuelAction;

public class PortablePumpjack extends AbstractInventorRelic {
    public static final String ID = "PortablePumpjack";
    public static final int AMOUNT = 1;

    public PortablePumpjack() {
        super(ID, RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + AMOUNT + this.DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PortablePumpjack();
    }

    @Override
    public void atTurnStart() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new GainFuelAction(AMOUNT));
    }
}

