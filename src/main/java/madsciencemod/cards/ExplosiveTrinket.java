package madsciencemod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import madsciencemod.actions.common.ApplyBombAction;

public class ExplosiveTrinket extends AbstractTrinket {
    public static final String ID = "ExplosiveTrinket";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final int BOMB_AMT = 10;
    private static final int UPGRADE_BOMB_AMT = 5;
    private static final int TURNS = 3;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public ExplosiveTrinket() {
        super(ID, NAME, COST, DESCRIPTION, TYPE, TARGET);
        this.magicNumber = this.baseMagicNumber = BOMB_AMT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyBombAction(m, p, this.magicNumber, TURNS));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ExplosiveTrinket();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_BOMB_AMT);
        }
    }
}
