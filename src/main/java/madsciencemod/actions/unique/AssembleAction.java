package madsciencemod.actions.unique;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import madsciencemod.Utils;
import madsciencemod.cards.FusedTrinket;
import madsciencemod.relics.PolishingWheel;

public class AssembleAction extends AbstractGameAction {
    private float startingDuration;
    // number of parts still to add
    private int parts;
    private boolean spendAllEnergy;
    // attributes of generated card
    private int energy = 0;
    private int draw   = 0;
    private int fuel   = 0;
    private int block  = 0;
    private int damage = 0;

    public AssembleAction(int parts, int amount, boolean spendAllEnergy) {
        this.parts = parts;
        this.amount = amount;
        this.spendAllEnergy = spendAllEnergy;
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.duration = this.startingDuration = Settings.ACTION_DUR_XFAST;
    }

    @Override
    public void update() {
        // a part was selected
        if (AbstractDungeon.cardRewardScreen.codexCard != null) {
            FusedTrinket t = (FusedTrinket)AbstractDungeon.cardRewardScreen.codexCard.makeStatEquivalentCopy();
            energy += t.energy;
            draw   += t.draw;
            fuel   += t.fuel;
            block  += t.baseBlock;
            damage += t.baseDamage;
            AbstractDungeon.cardRewardScreen.codexCard = null;
        }
        if (parts > 0) {
            parts--;
            ArrayList<AbstractCard> choices = new ArrayList<>();
            ArrayList<Integer> seen = new ArrayList<>();
            while (choices.size() < 3) {
                int r = MathUtils.random(4); // note: range is inclusive
                if (seen.contains(r)) continue;
                seen.add(r);
                AbstractCard card;
                switch (r) {
                    case 0:
                        card = new FusedTrinket(1,0,0,0,0);
                        break;
                    case 1:
                        card = new FusedTrinket(0,1,0,0,0);
                        break;
                    case 2:
                        card = new FusedTrinket(0,0,1,0,0);
                        break;
                    case 3:
                        card = new FusedTrinket(0,0,0,4,0);
                        break;
                    case 4:
                        card = new FusedTrinket(0,0,0,0,5);
                        break;
                    default:
                        card = new FusedTrinket(0,0,0,0,0);
                }
                if (AbstractDungeon.player.hasRelic(PolishingWheel.ID)) {
                    card.upgrade();
                }
                choices.add(card);
            }
            Utils.openCardRewardsScreen(choices, false);
            return;
        } else if (this.duration == this.startingDuration) {
            FusedTrinket card = new FusedTrinket(energy,draw,fuel,block,damage);
            if (AbstractDungeon.player.hasRelic(PolishingWheel.ID)) {
                // only upgrade name, effects were already upgraded before assembling
                card.upgradeNameOnly();
            }
            AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction(card, amount, true, true));
            if (spendAllEnergy) {
                AbstractDungeon.player.energy.use(EnergyPanel.totalCount);
            }
            this.isDone = true;
        }
        tickDuration();
    }
}
