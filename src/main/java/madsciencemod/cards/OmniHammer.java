package madsciencemod.cards;

import com.megacrit.cardcrawl.actions.defect.ReinforcedBodyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import madsciencemod.actions.common.ChooseAction;
import madsciencemod.actions.unique.OmniHammerAttackAction;

public class OmniHammer extends AbstractMadScienceCard {
    public static final String ID = "OmniHammer";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = -1;
    private static final int BLOCK = 6;
    private static final int UPGRADE_BLOCK = 2;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_DAMAGE = 7;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public OmniHammer() {
        super(ID, NAME, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseBlock = BLOCK;
        this.baseDamage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ChooseAction choice = new ChooseAction(this, m, EXTENDED_DESCRIPTION[0]);
        choice.add(EXTENDED_DESCRIPTION[1], EXTENDED_DESCRIPTION[2], () -> {
            AbstractDungeon.actionManager.addToBottom(new OmniHammerAttackAction(p, this.damage, this.damageTypeForTurn, this.freeToPlayOnce, this.energyOnUse));
        });
        choice.add(EXTENDED_DESCRIPTION[3], EXTENDED_DESCRIPTION[4], () -> {
            AbstractDungeon.actionManager.addToBottom(new ReinforcedBodyAction(p, this.block, this.freeToPlayOnce, this.energyOnUse));
        });
        AbstractDungeon.actionManager.addToBottom(choice);
    }

    @Override
    public AbstractCard makeCopy() {
        return new OmniHammer();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeDamage(UPGRADE_BLOCK);
            this.upgradeDamage(UPGRADE_DAMAGE);
        }
    }
}
