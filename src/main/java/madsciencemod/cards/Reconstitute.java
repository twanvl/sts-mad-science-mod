package madsciencemod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import madsciencemod.actions.common.GainFuelAction;
import madsciencemod.powers.FuelPower;
import madsciencemod.powers.ReconstitutePower;

public class Reconstitute extends AbstractMadScienceCard {
    public static final String ID = "MadScienceMod:Reconstitute";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 0;
    private static final int MULTIPLIER = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Reconstitute() {
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = MULTIPLIER;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            int gain = FuelPower.fuelSpentThisTurn * this.magicNumber;
            AbstractDungeon.actionManager.addToBottom(new GainFuelAction(gain));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ReconstitutePower(p, this.magicNumber), this.magicNumber));
        }
    }

    @Override
    public void applyPowers() {
        if (this.upgraded) {
            int gain = FuelPower.fuelSpentThisTurn * this.magicNumber;
            this.rawDescription = UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[0] + gain + EXTENDED_DESCRIPTION[1];
            this.initializeDescription();
        }
    }

    @Override
    public void onMoveToDiscard() {
        if (this.upgraded) {
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Reconstitute();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}