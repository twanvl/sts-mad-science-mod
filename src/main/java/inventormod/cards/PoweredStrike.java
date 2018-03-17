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

import basemod.abstracts.CustomCard;
import inventormod.patches.CardColorEnum;
import inventormod.powers.FuelPower;

public class PoweredStrike extends CustomCard {
    public static final String ID = "Powered Strike";
    public static final String IMAGE = "img/cards/PoweredStrike.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
    private static final int FUEL_COST = 1;
	private static final int ATTACK_DMG = 10;
	private static final int UPGRADE_ATTACK_DMG = 5;
	private static final int POOL = 0;

	public PoweredStrike() {
		super(ID, NAME, IMAGE, COST, DESCRIPTION, AbstractCard.CardType.ATTACK, CardColorEnum.BRONZE, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.ENEMY, POOL);
		this.baseDamage = ATTACK_DMG;
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FuelPower(p, -FUEL_COST)));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,new DamageInfo(p, this.damage, this.damageTypeForTurn),AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
	}

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return super.canUse(p,m) && FuelPower.haveEnough(p,FUEL_COST);
    }

	public AbstractCard makeCopy() {
		return new PoweredStrike();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_ATTACK_DMG);
		}
	}
}
