package inventormod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import inventormod.patches.CardColorEnum;
import inventormod.powers.FuelPower;

public class PoweredDefend extends CustomCard {
    public static final String ID = "Powered Defend";
    public static final String IMAGE = "img/cards/PoweredDefend.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int FUEL_COST = 1;
    private static final int BLOCK_AMT = 9;
    private static final int UPGRADE_BLOCK_AMT = 4;
    private static final int POOL = 0;

    public PoweredDefend() {
        super(ID, NAME, IMAGE, COST, DESCRIPTION, AbstractCard.CardType.SKILL, CardColorEnum.BRONZE, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.SELF, POOL);
        this.baseBlock = BLOCK_AMT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FuelPower(p, -FUEL_COST)));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return super.canUse(p,m) && FuelPower.haveEnough(p,FUEL_COST);
    }

    @Override
    public AbstractCard makeCopy() {
        return new PoweredDefend();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_BLOCK_AMT);
        }
    }
}