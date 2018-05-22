package inventormod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import inventormod.powers.LightOnFirePower;

public class LightOnFire extends AbstractMadScienceCard {
    public static final String ID = "LightOnFire";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static final int ATTACK_DMG = 12;
    private static final int UPGRADE_ATTACK_DMG = 3;
    private static final int FIRE_DMG = 10;
    private static final int TURNS = 1;
    private static final int UPGRADE_TURNS = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    public int turns = TURNS;

    public LightOnFire() {
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseDamage = ATTACK_DMG;
        this.magicNumber = this.baseMagicNumber = FIRE_DMG;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new LightOnFirePower(m, this.magicNumber, turns), turns));
    }

    @Override
    public AbstractCard makeCopy() {
        return new LightOnFire();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_ATTACK_DMG);
            turns += UPGRADE_TURNS;
            rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
