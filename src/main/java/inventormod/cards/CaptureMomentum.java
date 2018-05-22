package inventormod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import inventormod.powers.CaptureMomentumPower;

public class CaptureMomentum extends AbstractMadScienceCard {
    public static final String ID = "CaptureMomentum";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int MULTIPLY = 2;
    private static final int UPGRADE_MULTIPLY = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public CaptureMomentum() {
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = MULTIPLY;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new CaptureMomentumPower(mo, this.magicNumber), this.magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new CaptureMomentum();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_MULTIPLY);
        }
    }
}