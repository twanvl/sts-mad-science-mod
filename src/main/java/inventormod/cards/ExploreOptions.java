package inventormod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ExploreOptions extends AbstractMadScienceCard {
    public static final String ID = "ExploreOptions";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int DRAW_AMT = 2;
    private static final int UPGRADE_DRAW_AMT = 0;
    private static final int BLOCK = 3;
    private static final int UPGRADE_BLOCK = 2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ExploreOptions() {
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = DRAW_AMT;
        this.baseBlock = BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
    }

    @Override
    public void triggerWhenDrawn() {
        this.applyPowers();
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ExploreOptions();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_BLOCK);
            this.upgradeMagicNumber(UPGRADE_DRAW_AMT);
        }
    }
}