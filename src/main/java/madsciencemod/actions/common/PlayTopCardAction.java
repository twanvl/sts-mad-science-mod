package madsciencemod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PlayTopCardAction extends AbstractGameAction {
    private int havocTimes;

    public PlayTopCardAction(AbstractCreature target, int times) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = target;
        this.havocTimes = times;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size() == 0) {
                this.isDone = true;
                return;
            }
            if (AbstractDungeon.player.drawPile.isEmpty()) {
                AbstractDungeon.actionManager.addToTop(new PlayTopCardAction(this.target, this.havocTimes));
                AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
                this.isDone = true;
                return;
            }
            for (int i = 0; i < this.havocTimes; ++i) {
                if (AbstractDungeon.player.drawPile.isEmpty()) continue;
                AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
                AbstractDungeon.player.drawPile.group.remove(card);
                AbstractDungeon.getCurrRoom().souls.remove(card);
                card.freeToPlayOnce = true;
                AbstractDungeon.player.limbo.group.add(card);
                card.current_y = -200.0f * Settings.scale;
                card.target_x = (float)Settings.WIDTH / 2.0f + (float)(i * 200);
                card.target_y = (float)Settings.HEIGHT / 2.0f;
                card.targetAngle = 0.0f;
                card.lighten(false);
                card.drawScale = 0.12f;
                card.targetDrawScale = 0.75f;
                if (!card.canUse(AbstractDungeon.player, (AbstractMonster)this.target)) {
                    //AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.limbo));
                    // TODO: discard card?
                    AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));
                    continue;
                }
                card.applyPowers();
                AbstractDungeon.actionManager.addToTop(new QueueCardAction(card, this.target));
                AbstractDungeon.actionManager.addToTop(new WaitAction(0.8f));
                AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));
            }
            this.isDone = true;
        }
    }
}

