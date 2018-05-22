package inventormod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import inventormod.actions.unique.GattlingGunAction;
import inventormod.powers.FuelPower;

public class GattlingGun extends AbstractMadScienceCard {
    public static final String ID = "GattlingGun";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 3;
    private static final int UPGRADE_ATTACK_DMG = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public GattlingGun() {
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseDamage = ATTACK_DMG;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GattlingGunAction(p, m, FuelPower.currentAmount(), this.damage, this.damageTypeForTurn));
    }

    @Override
    public AbstractCard makeCopy() {
        return new GattlingGun();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_ATTACK_DMG);
        }
    }
}
