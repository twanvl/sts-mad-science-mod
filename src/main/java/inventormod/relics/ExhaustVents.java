package inventormod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import inventormod.powers.FuelPower;

public class ExhaustVents extends AbstractMadScienceRelic implements FuelPower.Listener {
    public static final String ID = "ExhaustVents";
    public static final int AMOUNT = 2;

    public ExhaustVents() {
        super(ID, RelicTier.COMMON, LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + AMOUNT + this.DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ExhaustVents();
    }

    @Override
    public void onFuelChange(int change) {
        AbstractPlayer source = null;
        if (change < 0 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (m.isDead || m.isDying) continue;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, source, new PoisonPower(m, source, AMOUNT), AMOUNT));
            }
        }
    }
}

