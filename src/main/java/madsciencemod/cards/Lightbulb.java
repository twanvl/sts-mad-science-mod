package madsciencemod.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Lightbulb extends AbstractMadScienceCard {
    public static final String ID = "Lightbulb";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static final int FUEL_COST = 2;
    private static final int UPGRADE_FUEL_COST = -1;
    private static final int ENERGY_AMT = 2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;

    public Lightbulb() {
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, CardTarget.SELF);
        this.fuelCost = this.magicNumber = this.baseMagicNumber = FUEL_COST;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(ENERGY_AMT));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Lightbulb();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeMagicNumber(UPGRADE_FUEL_COST);
            this.fuelCost = this.magicNumber;
        }
    }
}
