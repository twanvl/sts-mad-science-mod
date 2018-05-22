package inventormod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import inventormod.actions.common.ChooseAction;

public class MakeOrBreak extends AbstractMadScienceCard {
    public static final String ID = "MakeOrBreak";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 2;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_DAMAGE = 2;
    private static final int ATTACK_TIMES = 2;
    private static final int DRAW = 2;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;

    public MakeOrBreak() {
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, CardTarget.ENEMY);
        this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = DRAW;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ChooseAction choice = new ChooseAction(this, m, EXTENDED_DESCRIPTION[0]);
        choice.add(EXTENDED_DESCRIPTION[1], EXTENDED_DESCRIPTION[2], () -> {
            for (int i = 0; i < ATTACK_TIMES; i++) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
        });
        choice.add(EXTENDED_DESCRIPTION[3], EXTENDED_DESCRIPTION[4], () -> {
            for (int i = 0; i < ATTACK_TIMES; i++) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, this.magicNumber), this.magicNumber));
        });
        AbstractDungeon.actionManager.addToBottom(choice);
    }

    @Override
    public AbstractCard makeCopy() {
        return new MakeOrBreak();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE);
        }
    }
}
