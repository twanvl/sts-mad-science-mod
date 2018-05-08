package inventormod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class JunkLaser extends AbstractInventorCard {
    public static final String ID = "JunkLaser";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static final int DAMAGE = 14;
    private static final int UPGRADE_DAMAGE = 0;
    private static final int EXTRA_DAMAGE = 2;
    private static final int UPGRADE_EXTRA_DAMAGE = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public JunkLaser() {
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = EXTRA_DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int totalDamage = this.damage + this.magicNumber * countCards();
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, totalDamage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    // TODO: maybe an indicator of the actual damage?
    // TODO: correctly apply powers after calculating damage.

    public static int countCards() {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (isJunkCard(c)) count++;
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (isJunkCard(c)) count++;
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (isJunkCard(c)) count++;
        }
        return count;
    }
    public static boolean isJunkCard(AbstractCard card) {
        return card.cost == 0;
    }

    @Override
    public AbstractCard makeCopy() {
        return new JunkLaser();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE);
            this.upgradeMagicNumber(UPGRADE_EXTRA_DAMAGE);
        }
    }
}
