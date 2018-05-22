package madsciencemod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import madsciencemod.actions.common.GainFuelAction;

public class ExtractOilPower extends AbstractMadSciencePower {
    public static final String POWER_ID = "ExtractOil";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ExtractOilPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, owner, amount);
        this.updateDescription();
        this.isTurnBased = true;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == CardType.ATTACK) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new GainFuelAction(amount));
        }
    }
}

