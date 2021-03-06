package madsciencemod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import madsciencemod.patches.CardTagsEnum;
import madsciencemod.powers.RecyclingStationPower;

public abstract class AbstractTrinket extends AbstractMadScienceCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("MadScienceMod:AbstractTrinket"); // used for ShuffleTrinketAction
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    public AbstractTrinket(String id, String name, String image, int cost, String rawDescription, CardType type, AbstractCard.CardTarget target) {
        super(id, name, image, cost, rawDescription, type, CardColor.COLORLESS, CardRarity.SPECIAL, target);
        this.exhaust = true;
        this.tags.add(CardTagsEnum.TRINKET_TAG);
    }

    public AbstractTrinket(String id, String name, int cost, String rawDescription, CardType type, AbstractCard.CardTarget target) {
        super(id, name, cost, rawDescription, type, CardColor.COLORLESS, CardRarity.SPECIAL, target);
        this.exhaust = true;
        this.tags.add(CardTagsEnum.TRINKET_TAG);
    }

    @Override
    public void triggerWhenDrawn() {
        AbstractPower power = AbstractDungeon.player.getPower(RecyclingStationPower.POWER_ID);
        if (power != null && !AbstractDungeon.player.hasPower("No Draw")) {
            power.flash();
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, power.amount));
        }
    }
}
