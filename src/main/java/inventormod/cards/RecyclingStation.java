package inventormod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import inventormod.actions.common.ShuffleTrinketAction;
import inventormod.powers.RecyclingStationPower;

public class RecyclingStation extends AbstractMadScienceCard {
    public static final String ID = "RecyclingStation";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;
    private static final int AMOUNT = 1;
    private static final int TRINKET_AMT = 0;
    private static final int UPGRADE_TRINKET_AMT = 2;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public RecyclingStation() {
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = TRINKET_AMT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RecyclingStationPower(p,AMOUNT), AMOUNT));
        AbstractDungeon.actionManager.addToBottom(new ShuffleTrinketAction(this.magicNumber, true, true));
    }

    @Override
    public AbstractCard makeCopy() {
        return new RecyclingStation();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeBaseCost(UPGRADED_COST);
            this.upgradeMagicNumber(UPGRADE_TRINKET_AMT);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
