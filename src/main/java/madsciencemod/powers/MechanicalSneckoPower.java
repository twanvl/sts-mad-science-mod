package madsciencemod.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import madsciencemod.actions.common.RandomizeCostForTurnAction;

public class MechanicalSneckoPower extends AbstractMadSciencePower {
    public static final String POWER_ID = "MadScienceMod:MechanicalSnecko";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public int draw = 0;
    public static final int INTERVAL = 2;

    public MechanicalSneckoPower(AbstractCreature owner, int draw) {
        super(POWER_ID, NAME, owner);
        this.draw = draw;
        this.amount = INTERVAL;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + (this.amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2]) + DESCRIPTIONS[3] + this.draw + (this.draw == 1 ? DESCRIPTIONS[4] : DESCRIPTIONS[5]) + DESCRIPTIONS[6];
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0f;
        this.draw += stackAmount;
        this.updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        --this.amount;
        if (this.amount == 0) {
            this.flash();
            this.amount = INTERVAL;
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, draw));
            AbstractDungeon.actionManager.addToBottom(new RandomizeCostForTurnAction());
        }
    }

    @Override
    public void atStartOfTurn() {
        this.amount = INTERVAL;
        this.updateDescription();
    }
}
