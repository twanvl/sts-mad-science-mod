package madsciencemod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import madsciencemod.actions.common.ChooseAction;
import madsciencemod.actions.common.GainFuelAction;
import madsciencemod.powers.FuelPower;

public class FlamableWall extends AbstractMadScienceCard {
    public static final String ID = "FlamableWall";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 2;
    private static final int FUEL_COST = 1;
    private static final int FUEL_AMT = 3;
    private static final int UPGRADE_FUEL_AMT = 1;
    private static final int BLOCK_AMT = 15;
    private static final int UPGRADE_BLOCK_AMT = 3;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public FlamableWall() {
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = FUEL_AMT;
        this.baseBlock = this.block = BLOCK_AMT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Two options
        ChooseAction choice = new ChooseAction(this, m, EXTENDED_DESCRIPTION[0]);
        if (FuelPower.currentAmount(p) >= FUEL_COST) {
            choice.add(EXTENDED_DESCRIPTION[1], EXTENDED_DESCRIPTION[2], () -> {
                FuelPower.spendFuel(FUEL_COST);
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
            });
        }
        choice.add(EXTENDED_DESCRIPTION[3], EXTENDED_DESCRIPTION[4], () -> {
            AbstractDungeon.actionManager.addToBottom(new GainFuelAction(this.magicNumber));
        });
        AbstractDungeon.actionManager.addToBottom(choice);
    }

    @Override
    public AbstractCard makeCopy() {
        return new FlamableWall();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_BLOCK_AMT);
            this.upgradeMagicNumber(UPGRADE_FUEL_AMT);
        }
    }
}