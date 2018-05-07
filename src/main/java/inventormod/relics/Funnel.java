package inventormod.relics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import inventormod.InventorMod;
import inventormod.powers.FuelPower;

public class Funnel extends CustomRelic {
    public static final String ID = "Funnel";
    private static final String IMAGE = InventorMod.relicImage(ID);

    public Funnel() {
        super(ID, new Texture(Gdx.files.internal(IMAGE)), RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Funnel();
    }
}

