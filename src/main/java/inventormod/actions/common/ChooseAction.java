package inventormod.actions.common;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import inventormod.InventorMod;

// Show a screen to let the player pick from several actions, displayed as cards
public class ChooseAction extends AbstractGameAction {
    AbstractCard baseCard;
    AbstractMonster target;
    CardGroup choices = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    ArrayList<Runnable> actions = new ArrayList<>();
    String message;

    public ChooseAction(AbstractCard baseCard, AbstractMonster target, String message) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, 1);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.baseCard = baseCard;
        this.message = message;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    public void add(String name, String description, Runnable action) {
        AbstractCard choice = baseCard.makeStatEquivalentCopy();
        choice.name = name;
        choice.rawDescription = description;
        choice.initializeDescription();
        choice.calculateCardDamage(target);
        choices.addToTop(choice);
        actions.add(action);
    }

    @Override
    public void update() {
        if (choices.isEmpty()) {
            this.tickDuration();
            this.isDone = true;
            return;
        }
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            if (choices.size() > 1) {
                AbstractDungeon.gridSelectScreen.open(this.choices, 1, message, false, false, false, false);
                this.tickDuration();
                return;
            } else {
                actions.get(0).run();
                this.tickDuration();
                this.isDone = true;
                return;
            }
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard pick = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            int i = choices.group.indexOf(pick);
            InventorMod.logger.info("Picked option: " + i);
            actions.get(i).run();
        }
        this.tickDuration();
    }
}