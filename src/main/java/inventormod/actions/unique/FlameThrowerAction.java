package inventormod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import inventormod.powers.FuelPower;

public class FlameThrowerAction extends AbstractGameAction {
    private float startingDuration;
    private AbstractPlayer p;
    private AbstractMonster m;
    int times, damage;
    DamageInfo.DamageType damageType;
    boolean freeToPlayOnce;

    public FlameThrowerAction(AbstractPlayer p, AbstractMonster m, int damage, DamageInfo.DamageType damageType, boolean freeToPlayOnce) {
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.p = p;
        this.m = m;
        this.times = FuelPower.currentAmount();
        this.damage = damage;
        this.damageType = damageType;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = this.startingDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == this.startingDuration) {
            if (!freeToPlayOnce) {
                FuelPower.spendFuel(Math.min(times, FuelPower.currentAmount()));
            }
            for (int i = 0; i < times; ++i) {
                AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, this.damage, this.damageType), AbstractGameAction.AttackEffect.FIRE));
            }
            this.isDone = true;
        }
        this.tickDuration();
    }
}