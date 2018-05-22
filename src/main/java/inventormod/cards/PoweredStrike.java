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

public class PoweredStrike extends AbstractMadScienceCard {
    public static final String ID = "PoweredStrike";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int FUEL_COST = 1;
    private static final int ATTACK_DMG = 10;
    private static final int UPGRADE_ATTACK_DMG = 5;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.BASIC;

    public PoweredStrike() {
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, CardTarget.ENEMY);
        this.baseDamage = ATTACK_DMG;
        this.fuelCost = FUEL_COST;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,new DamageInfo(p, this.damage, this.damageTypeForTurn),AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public AbstractCard makeCopy() {
        return new PoweredStrike();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_ATTACK_DMG);
        }
    }
}
