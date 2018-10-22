package madsciencemod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import madsciencemod.actions.common.ApplyBombAction;

public class CaptureMomentumPower extends AbstractMadSciencePower {
    public static final String POWER_ID = "MadScienceMod:CaptureMomentum";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final int TURNS = 3;

    public CaptureMomentumPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, owner, amount);
        this.type = AbstractPower.PowerType.DEBUFF;
        this.isTurnBased = true;
        this.updateDescription();
        this.priority = 101;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    // We can only implement this by attaching it to the monsters, because there we can modify incomming damage
    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner instanceof AbstractPlayer) {
            this.flash();
            damageAmount *= this.amount;
            AbstractDungeon.actionManager.addToBottom(new ApplyBombAction(this.owner, info.owner, damageAmount, TURNS));
            return 0;
        }
        return damageAmount;
    }

    @Override
    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }
}
