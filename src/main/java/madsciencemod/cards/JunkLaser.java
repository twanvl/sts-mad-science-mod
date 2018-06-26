package madsciencemod.cards;

import java.util.ArrayList;
import java.util.List;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.TooltipInfo;

public class JunkLaser extends AbstractMadScienceCard {
    public static final String ID = "JunkLaser";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 2;
    private static final int DAMAGE = 14;
    private static final int EXTRA_DAMAGE = 3;
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
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        this.initializeDescription();
    }

    @Override
    public void applyPowers() {
        this.baseDamage = DAMAGE + this.magicNumber * countCards();
        super.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.baseDamage = DAMAGE + this.magicNumber * countCards();
        super.calculateCardDamage(mo);
    }

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
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0],EXTENDED_DESCRIPTION[1]));
        return tips;
    }

    @Override
    public AbstractCard makeCopy() {
        return new JunkLaser();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_EXTRA_DAMAGE);
        }
    }
}
