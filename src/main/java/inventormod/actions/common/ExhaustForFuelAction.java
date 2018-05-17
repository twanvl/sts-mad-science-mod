package inventormod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExhaustForFuelAction extends AbstractGameAction {
    public static final String[] TEXT = ExhaustAction.TEXT;
    private AbstractPlayer p;
    private boolean anyNumber;
    private boolean canPickZero = true;
    private int fuelPerCard;

    public ExhaustForFuelAction(AbstractPlayer p, int amount, boolean anyNumber, int fuelPerCard) {
        this.p = p;
        this.anyNumber = anyNumber;
        this.fuelPerCard = fuelPerCard;
        this.setValues(p, p, amount);
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.EXHAUST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.size() == 0) {
                this.isDone = true;
                return;
            } else if (!this.anyNumber && this.p.hand.size() <= this.amount) {
                int tmp = this.p.hand.size();
                for (int i = 0; i < tmp; ++i) {
                    AbstractCard c = this.p.hand.getTopCard();
                    this.p.hand.moveToExhaustPile(c);
                }
                CardCrawlGame.dungeon.checkForPactAchievement();
                AbstractDungeon.actionManager.addToTop(new GainFuelAction(tmp * fuelPerCard));
                this.isDone = true;
                return;
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, this.anyNumber, this.canPickZero);
                this.tickDuration();
                return;
            }
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                this.p.hand.moveToExhaustPile(c);
            }
            CardCrawlGame.dungeon.checkForPactAchievement();
            AbstractDungeon.actionManager.addToTop(new GainFuelAction(AbstractDungeon.handCardSelectScreen.selectedCards.group.size() * fuelPerCard));
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        this.tickDuration();
    }
}

