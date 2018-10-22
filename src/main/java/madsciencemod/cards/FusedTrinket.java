package madsciencemod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import madsciencemod.MadScienceMod;
import madsciencemod.actions.common.GainFuelAction;

public class FusedTrinket extends AbstractTrinket {
    public static final String ID = "MadScienceMod:FusedTrinket";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 0;

    public int energy;
    public int draw;
    public int fuel;

    public FusedTrinket(boolean empty) {
        this(empty ? 0 : 1, empty ? 0 : 1, empty ? 0 : 1, empty ? 0 : 3, empty ? 0 : 4);
    }

    public FusedTrinket(int energy, int draw, int fuel, int baseBlock, int baseDamage) {
        super(ID, NAME, MadScienceMod.cardImage(baseDamage > 0 ? ID + "Attack" : ID), COST, "", baseDamage > 0 ? CardType.ATTACK : CardType.SKILL, baseDamage > 0 ? CardTarget.ENEMY : CardTarget.SELF);
        this.energy = energy;
        this.draw = draw;
        this.fuel = fuel;
        this.baseBlock = baseBlock;
        this.baseDamage = baseDamage;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (energy > 0) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(energy));
        }
        if (fuel > 0) {
            AbstractDungeon.actionManager.addToBottom(new GainFuelAction(fuel));
        }
        if (block > 0) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        }
        if (draw > 0) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, draw));
        }
        if (damage > 0) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, this.damageTypeForTurn), AttackEffect.SLASH_DIAGONAL));
        }
    }

    @Override
    public void initializeDescription() {
        StringBuilder desc = new StringBuilder();
        if (energy > 0) {
            desc.append(EXTENDED_DESCRIPTION[0]);
            for (int i = 0; i < energy; ++i) desc.append(" [E]");
            desc.append(EXTENDED_DESCRIPTION[1]);
        }
        if (fuel > 0) {
            if (desc.length() > 0) desc.append(" NL ");
            desc.append(EXTENDED_DESCRIPTION[2] + fuel + EXTENDED_DESCRIPTION[3]);
        }
        if (baseBlock > 0) {
            if (desc.length() > 0) desc.append(" NL ");
            desc.append(EXTENDED_DESCRIPTION[4]);
        }
        if (draw > 0) {
            if (desc.length() > 0) desc.append(" NL ");
            desc.append(EXTENDED_DESCRIPTION[5] + draw + (draw == 1 ? EXTENDED_DESCRIPTION[6] : EXTENDED_DESCRIPTION[7]));
        }
        if (baseDamage > 0) {
            if (desc.length() > 0) desc.append(" NL ");
            desc.append(EXTENDED_DESCRIPTION[8]);
        }
        if (desc.length() > 0) desc.append(" NL ");
        desc.append(EXTENDED_DESCRIPTION[9]);
        rawDescription = desc.toString();
        super.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new FusedTrinket(energy,draw,fuel,baseBlock,baseDamage);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            if (baseDamage > 0) this.baseDamage += 2;
            if (baseBlock > 0) this.baseBlock += 2;
            initializeDescription();
        }
    }

    public void upgradeNameOnly() {
        if (!this.upgraded) {
            upgradeName();
        }
    }
}
