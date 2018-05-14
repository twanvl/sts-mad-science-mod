package inventormod.relics;

import java.lang.reflect.Method;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import inventormod.InventorMod;

public class InfiniteJournal extends AbstractInventorRelic {
    public static final String ID = "InfiniteJournal";

    public InfiniteJournal() {
        super(ID, RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new InfiniteJournal();
    }

    // Replaces card.canUpgrade()
    public static boolean canUpgradeCard(AbstractCard c) {
        if (c.canUpgrade()) {
            return true; // normal upgrade path
        } else if (AbstractDungeon.player.hasRelic(ID)) {
            return canUpgradeCardAgain(c);
        } else {
            return false;
        }
    }
    private static boolean canUpgradeCardAgain(AbstractCard c) {
        if (!c.upgraded) {
            // only upgrade already upgraded cards again. So no curses etc.
            return false;
        }
        if (c.upgradedMagicNumber && !c.rawDescription.contains("!M!")) {
            // Upgrade changed magic number, but this is not reflected in the card textbox
            // for instance TURBO increases mana gained
            return false;
        }
        // upgrading changes block/damage/magicNumber, so doing it again makes sense
        return (c.upgradedDamage || c.upgradedBlock || c.upgradedMagicNumber);
    }

    // Replaces card.upgrade()
    public static void upgradeCard(AbstractCard c) {
        if (c.canUpgrade()) {
            // normal upgrade path
            c.upgrade();
        } else if (AbstractDungeon.player.hasRelic(ID) && canUpgradeCardAgain(c)) {
            upgradeCardAgain(c);
        }
    }
    private static void upgradeCardAgain(AbstractCard c) {
        // to upgrade a card again, simply convince the logic that the card is not upgraded
        c.upgraded = false;
        int oldMagicNumber = c.baseMagicNumber;
        c.upgrade();
        // set the name
        String baseName = c.makeCopy().name;
        c.name = baseName + "+" + c.timesUpgraded;
        initializeCardTitle(c);
        if (oldMagicNumber > 0 && c.baseMagicNumber <= 0) {
            // Note: some cards decrease magicNumber, we probably don't want it to go at or below 0
            //  Aggragate: gets div by 0!
            //  Concentrate: discards -1 cards, which has separate code path
            //  HemoKinisis: gets warning if self damage<0
            c.magicNumber = c.baseMagicNumber = oldMagicNumber;
        }
    }
    private static void initializeCardTitle(AbstractCard card) {
        try {
            Method method = AbstractCard.class.getDeclaredMethod("initializeTitle");
            method.setAccessible(true);
            method.invoke(card);
         } catch (Exception ex) {
            InventorMod.logger.error("Exception occured when calling initializeCard", ex);
         }
    }

    // Note: UpgradePatches makes sure that canUpgradeCard and upgradeCard are called
}

