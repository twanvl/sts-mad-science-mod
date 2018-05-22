package inventormod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import inventormod.powers.FuelPower;

public class Assemble extends AbstractMadScienceCard {
    public static final String ID = "Assemble";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK = 2;
    private static final int BLOCK_PER_FUEL = 2;
    private static final int UPGRADE_BLOCK_PER_FUEL = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Assemble() {
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseBlock = BLOCK;
        this.baseMagicNumber = this.magicNumber = BLOCK_PER_FUEL;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void applyPowers() {
        this.baseBlock = BLOCK + FuelPower.currentAmount() * this.magicNumber;
        super.applyPowers();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Assemble();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_BLOCK_PER_FUEL);
        }
    }
}