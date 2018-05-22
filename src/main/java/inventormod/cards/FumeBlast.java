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

import inventormod.powers.FuelPower;

public class FumeBlast extends AbstractMadScienceCard implements FuelPower.Listener {
    public static final String ID = "FumeBlast";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 4;
    private static final int ATTACK_DMG = 8;
    private static final int UPGRADE_ATTACK_DMG = 2;
    private static final int ATTACK_TIMES = 2;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public FumeBlast() {
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseDamage = ATTACK_DMG;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < ATTACK_TIMES; i++) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        }
    }

    @Override
    public void onFuelChange(int gain) {
        if (gain < 0) {
            this.updateCost(gain);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        FumeBlast tmp = new FumeBlast();
        if (AbstractDungeon.player != null) {
            tmp.updateCost(-FuelPower.fuelSpentThisCombat);
        }
        return tmp;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_ATTACK_DMG);
        }
    }
}
