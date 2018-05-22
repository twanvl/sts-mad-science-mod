package inventormod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class GarbageDisposalCannonPower extends AbstractMadSciencePower {
    public static final String POWER_ID = "GarbageDisposalCannon";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public GarbageDisposalCannonPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, owner, amount);
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description =  DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onExhaust(AbstractCard card) {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(this.amount), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }
}
