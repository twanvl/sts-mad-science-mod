package inventormod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import inventormod.powers.FuelPower;

public class GattlingGunAction extends AbstractGameAction {
    private float startingDuration;
    private AbstractPlayer p;
    private AbstractMonster m;
    int times, damage;
    DamageInfo.DamageType damageType;

    public GattlingGunAction(AbstractPlayer p, AbstractMonster m, int times, int damage, DamageInfo.DamageType damageType) {
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.p = p;
        this.m = m;
        this.times = times;
        this.damage = damage;
        this.damageType = damageType;
        this.duration = this.startingDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == this.startingDuration) {
            for (int i = 0; i < times; ++i) {
                AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, this.damage, this.damageType), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }
            this.isDone = true;
        }
        this.tickDuration();
    }
}