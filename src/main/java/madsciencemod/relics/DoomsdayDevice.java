package madsciencemod.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import madsciencemod.actions.common.ShuffleTrinketAction;

public class DoomsdayDevice extends AbstractMadScienceRelic {
    public static final String ID = "MadScienceMod:DoomsdayDevice";
    private static final int AMOUNT = 3;

    public DoomsdayDevice() {
        super(ID, RelicTier.COMMON, LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + AMOUNT + this.DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStartPreDraw() {
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new ShuffleTrinketAction(AMOUNT, false, false));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DoomsdayDevice();
    }
}
