package inventormod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import inventormod.actions.common.AllTrinketsToHandAction;
import inventormod.actions.common.ShuffleTrinketAction;

public class GatherParts extends AbstractMadScienceCard {
    public static final String ID = "GatherParts";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int TRINKETS = 3;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public GatherParts() {
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = TRINKETS;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new AllTrinketsToHandAction());
        AbstractDungeon.actionManager.addToBottom(new ShuffleTrinketAction(this.magicNumber, upgraded ? false : true, true));
    }

    @Override
    public AbstractCard makeCopy() {
        return new GatherParts();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
