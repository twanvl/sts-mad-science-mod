package inventormod.relics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import inventormod.InventorMod;
import inventormod.actions.common.GainFuelAction;

public class FuelTank extends CustomRelic {
    public static final String ID = "FuelTank";
    private static final String IMAGE = InventorMod.relicImage(ID);
    private static final int FUEL = 2;

    public FuelTank() {
        super(ID, new Texture(Gdx.files.internal(IMAGE)), RelicTier.STARTER, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + FUEL + this.DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToTop(new GainFuelAction(FUEL));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new FuelTank();
    }
}

