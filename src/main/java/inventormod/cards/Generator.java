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
import inventormod.powers.GeneratorPower;

public class Generator extends CustomCard {
	public static final String ID = "Generator";
	private static final String IMAGE = "img/cards/Generator.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 2;
	private static final int UPGRADED_COST = 1;
	private static final int AMOUNT = 1;
	private static final int POOL = 1;

	public Generator() {
		super(ID, NAME, IMAGE, COST, DESCRIPTION, AbstractCard.CardType.POWER, CardColorEnum.BRONZE, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF, POOL);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new GeneratorPower(p,AMOUNT), AMOUNT));
	}

	public AbstractCard makeCopy() {
		return new Generator();
	}

	public void upgrade() {
		if (!this.upgraded) {
            upgradeName();
            this.upgradeBaseCost(UPGRADED_COST);
		}
	}
}
