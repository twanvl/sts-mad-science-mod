package madsciencemod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import madsciencemod.cards.Weaponize;
import madsciencemod.cards.Weaponized;

public class WeaponizeAction extends AbstractGameAction {
    private float startingDuration;
    private int baseDamage;
    private boolean all;
    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotWeaponize = new ArrayList<>();

    public WeaponizeAction(int baseDamage, boolean all) {
        this.duration = 0.0f;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = this.startingDuration = Settings.ACTION_DUR_FAST;
        this.baseDamage = baseDamage;
        this.all = all;
        this.p = AbstractDungeon.player;
    }

    @Override
    public void update() {
        if (this.duration == this.startingDuration) {
            if (all) {
                ArrayList<AbstractCard> cardsToWeaponize = new ArrayList<>();
                for (AbstractCard c : this.p.hand.group) {
                    if (isWeaponizable(c)) {
                        cardsToWeaponize.add(c);
                    }
                }
                this.p.hand.group.removeAll(cardsToWeaponize);
                for (AbstractCard c : cardsToWeaponize) {
                    AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(new Weaponized(c,baseDamage)));
                }
                this.isDone = true;
                return;
            }
            for (AbstractCard c : this.p.hand.group) {
                if (!isWeaponizable(c)) {
                    cannotWeaponize.add(c);
                }
            }
            if (cannotWeaponize.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            } else if (cannotWeaponize.size() == this.p.hand.group.size() - 1) {
                for (AbstractCard c : this.p.hand.group) {
                    if (isWeaponizable(c)) {
                        this.p.hand.removeCard(c);
                        AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(new Weaponized(c,baseDamage)));
                        this.isDone = true;
                        return;
                    }
                }
            } else {
                this.p.hand.group.removeAll(cannotWeaponize);
                AbstractDungeon.handCardSelectScreen.open(Weaponize.EXTENDED_DESCRIPTION[0], 1, false, true, false, false, true);
                this.tickDuration();
                return;
            }
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : this.cannotWeaponize) {
                this.p.hand.addToTop(c);
            }
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(new Weaponized(c,baseDamage)));
            }
            this.p.hand.refreshHandLayout();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        this.tickDuration();
    }

    private boolean isWeaponizable(AbstractCard c) {
        return c.type == AbstractCard.CardType.SKILL && c.baseDamage == -1;
    }
}

