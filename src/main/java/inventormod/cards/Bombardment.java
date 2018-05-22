package inventormod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import inventormod.actions.common.ApplyBombAction;

public class Bombardment extends AbstractMadScienceCard {
    public static final String ID = "Bombardment";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int ATTACK_DMG = 10;
    private static final int BOMB_AMT = 15;
    private static final int UPGRADE_BOMB_AMT = 5;
    private static final int TURNS = 3;
    private static final int TURNS2 = 6;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public Bombardment() {
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = BOMB_AMT;
        this.baseDamage = ATTACK_DMG;
        this.isMultiDamage = true;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyBombAction(mo, p, this.magicNumber, TURNS));
            AbstractDungeon.actionManager.addToBottom(new ApplyBombAction(mo, p, this.magicNumber, TURNS2));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Bombardment();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_BOMB_AMT);
        }
    }
}