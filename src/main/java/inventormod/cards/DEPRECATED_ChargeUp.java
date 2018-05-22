package inventormod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import inventormod.powers.FuelPower;

public class DEPRECATED_ChargeUp extends AbstractMadScienceCard {
    public static final String ID = "DEPRECATED_ChargeUp";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int FUEL_COST = 1;
    private static final int UPGRADED_FUEL_COST = 0;
    private static final int STR_AMT = 2;
    private static final int DEX_AMT = 2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public DEPRECATED_ChargeUp() {
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = FUEL_COST;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, FuelPower.POWER_ID, FUEL_COST));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, STR_AMT), STR_AMT));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, DEX_AMT), DEX_AMT));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p,m)) return false;
        if (FuelPower.currentAmount(p) < FUEL_COST) {
            this.cantUseMessage = FuelPower.NOT_ENOUGH_FUEL_MESSAGE;
            return false;
        }
        return true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new DEPRECATED_ChargeUp();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADED_FUEL_COST);
        }
    }
}