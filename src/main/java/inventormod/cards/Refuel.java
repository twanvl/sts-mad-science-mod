package inventormod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import inventormod.patches.CardColorEnum;
import inventormod.powers.FuelPower;

public class Refuel extends CustomCard {
	public static final String ID = "Refuel";
	private static final String IMAGE = "img/cards/Refuel.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int FUEL_AMT = 3;
	private static final int UPGRADE_FUEL_AMT = 1;
	private static final int POOL = 1;

	public Refuel() {
		super(ID, NAME, IMAGE, COST, DESCRIPTION, AbstractCard.CardType.SKILL, CardColorEnum.BRONZE, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF, POOL);
        this.magicNumber = this.baseMagicNumber = FUEL_AMT;
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FuelPower(p, this.magicNumber), this.magicNumber));
	}

	public AbstractCard makeCopy() {
		return new Refuel();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
            this.upgradeMagicNumber(UPGRADE_FUEL_AMT);
		}
	}
}
