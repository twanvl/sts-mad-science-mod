package inventormod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Virus extends AbstractInventorCard {
    public static final String ID = "Virus";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static final int COPIES = 1;
    private static final int UPGRADE_COPIES = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Virus() {
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = COPIES;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(this.makeStatEquivalentCopy(), this.magicNumber));
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        this.baseDamage = virusCount();
        super.calculateCardDamage(m);
    }

    public static int virusCount() {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (!isVirus(c)) ++count;
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (!isVirus(c)) ++count;
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (!isVirus(c)) ++count;
        }
        return count;
    }
    public static boolean isVirus(AbstractCard c) {
        return c instanceof Virus;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Virus();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_COPIES);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
