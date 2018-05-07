package inventormod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import inventormod.powers.CloneVatsPower;

public class CloneVatsAction extends AbstractGameAction {
    private float startingDuration;
    private AbstractPlayer p;
    private int amount = 1;

    public CloneVatsAction(AbstractCreature source, int amount) {
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = AbstractGameAction.ActionType.DRAW;
        this.duration = this.startingDuration = Settings.ACTION_DUR_FAST;
        this.p = AbstractDungeon.player;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (this.duration == this.startingDuration) {
            if (this.p.hand.group.size() == 0) {
                this.isDone = true;
                this.tickDuration();
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(CloneVatsPower.DESCRIPTIONS[3], this.amount, false, true, false, true);
            this.tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        this.tickDuration();
    }
}
