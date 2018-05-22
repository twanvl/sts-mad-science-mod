package madsciencemod.actions.common;

import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerDebuffEffect;

import madsciencemod.powers.BombPower;

// ApplyPowerAction, specialized to BombPower
// we can't use ApplyPowerAction, because BombPower has two parameters (amount and turns)
public class ApplyBombAction extends AbstractGameAction {
    public static final String[] TEXT = ApplyPowerAction.TEXT;
    private float startingDuration;
    private int turns;

    public ApplyBombAction(AbstractCreature target, AbstractCreature source, int amount, int turns) {
        this.setValues(target, source, amount);
        this.turns = turns;
        this.actionType = AbstractGameAction.ActionType.POWER;
        this.duration = this.startingDuration = Settings.FAST_MODE ? 0.1f : Settings.ACTION_DUR_FAST;
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.duration = this.startingDuration = 0.0f;
            this.isDone = true;
        }
    }

    @Override
    public void update() {
        if (this.shouldCancelAction()) {
            this.isDone = true;
            return;
        }
        if (this.duration == this.startingDuration) {
            if (this.target.hasPower("Artifact")) {
                AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(this.target, TEXT[0]));
                this.duration -= Gdx.graphics.getDeltaTime();
                CardCrawlGame.sound.play("NULLIFY_SFX");
                this.target.getPower("Artifact").flashWithoutSound();
                this.target.getPower("Artifact").onSpecificTrigger();
                return;
            }
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
            this.target.useFastShakeAnimation(0.5f);
            // Apply
            AbstractPower p = this.target.getPower(BombPower.POWER_ID);
            if (p != null) {
                ((BombPower)p).stackPower(amount, turns);
                p.flash();
                p.updateDescription();
                AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0f, "+" + Integer.toString(this.amount) + " " + p.name));
            } else {
                p = new BombPower(target, amount, turns);
                this.target.powers.add(p);
                Collections.sort(this.target.powers);
                p.onInitialApplication();
                p.flash();
                AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0f, Integer.toString(this.amount) + " " + p.name));
            }
            AbstractDungeon.onModifyPower();
        }
        this.tickDuration();
    }
}
