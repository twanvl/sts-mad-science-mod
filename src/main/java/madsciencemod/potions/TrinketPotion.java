package madsciencemod.potions;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import madsciencemod.actions.common.ShuffleTrinketAction;

public class TrinketPotion extends AbstractPotion {
    public static final String POTION_ID = "MadScienceMod:TrinketPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public TrinketPotion() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.CARD, PotionColor.ENERGY);
        this.potency = this.getPotency();
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        this.isThrown = false;
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public void use(AbstractCreature target) {
        for (int i = 0 ; i < 3 ; ++i) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(ShuffleTrinketAction.getRandomTrinket(false)));
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new TrinketPotion();
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 3;
    }
}

