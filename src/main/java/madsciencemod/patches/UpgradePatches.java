package madsciencemod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

// Several patches to places where card.upgrade() and card.canUpgrade() are called
public class UpgradePatches {

    public static ExprEditor patch = new ExprEditor() {
        public void edit(MethodCall m) throws CannotCompileException {
            // replace  c.canUpgrade() by InfiniteJournal.canUpgradeCard(c)
            // replace: c.upgrade()    by InfiniteJournal.upgradeCard(c)
            if (m.getClassName().equals("com.megacrit.cardcrawl.cards.AbstractCard")) {
                if (m.getMethodName().equals("canUpgrade")) {
                    m.replace("$_ = madsciencemod.relics.InfiniteJournal.canUpgradeCard($0);");
                } else if (m.getMethodName().equals("upgrade")) {
                    m.replace("madsciencemod.relics.InfiniteJournal.upgradeCard($0);");
                }
            }
        }
    };

    // Actions

    @SpirePatch(cls="com.megacrit.cardcrawl.actions.unique.ApotheosisAction", method="upgradeAllCardsInGroup")
    public static class ApotheosisAction_upgradeAllCardsInGroup {
        public static ExprEditor Instrument() { return patch; }
    }
    @SpirePatch(cls="com.megacrit.cardcrawl.actions.unique.ArmamentsAction", method="update")
    public static class ArmamentsAction_update {
        public static ExprEditor Instrument() { return patch; }
    }
    @SpirePatch(cls="com.megacrit.cardcrawl.actions.unique.ExhumeAction", method="update")
    public static class ExhumeAction_update {
        public static ExprEditor Instrument() { return patch; }
    }
    // Note: SacrificeAction and TransmutationAction only upgrade unupgraded cards

    // Core logic

    @SpirePatch(cls="com.megacrit.cardcrawl.cards.AbstractCard", method="renderInLibrary")
    public static class AbstractCard_renderInLibrary {
        public static ExprEditor Instrument() { return patch; }
    }
    // Note: AbstractCard.renderCardTip only cares about rawDescription
    // Note: AbstractDungeon.getRewardCards() only upgrades fresh rewards
    @SpirePatch(cls="com.megacrit.cardcrawl.cards.CardGroup", method="getUpgradableCards")
    public static class CardGroup_getUpgradableCards {
        public static ExprEditor Instrument() { return patch; }
    }
    @SpirePatch(cls="com.megacrit.cardcrawl.cards.CardGroup", method="hasUpgradableCards")
    public static class CardGroup_hasUpgradableCards {
        public static ExprEditor Instrument() { return patch; }
    }
    @SpirePatch(cls="com.megacrit.cardcrawl.helpers.CardLibrary", method="getCopy", paramtypes={"String","int","int"})
    public static class CardLibrary_buttonEffect {
        public static ExprEditor Instrument() { return patch; }
    }
    // Note: CardLibrary.uploadCardData doesn't matter for a mod
    @SpirePatch(cls="com.megacrit.cardcrawl.screens.runHistory.RunHistoryScreen", method="cardForName")
    public static class RunHistoryScreen_cardForName {
        public static ExprEditor Instrument() { return patch; }
    }
    @SpirePatch(cls="com.megacrit.cardcrawl.screens.select.GridCardSelectScreen", method="update")
    public static class GridCardSelectScreen_update {
        public static ExprEditor Instrument() { return patch; }
    }
    @SpirePatch(cls="com.megacrit.cardcrawl.screens.select.HandCardSelectScreen", method="selectHoveredCard")
    public static class HandCardSelectScreen_selectHoveredCard {
        public static ExprEditor Instrument() { return patch; }
    }
    @SpirePatch(cls="com.megacrit.cardcrawl.screens.select.HandCardSelectScreen", method="updateMessage")
    public static class HandCardSelectScreen_updateMessage {
        public static ExprEditor Instrument() { return patch; }
    }
    @SpirePatch(cls="com.megacrit.cardcrawl.screens.SingleCardViewPopup", method="render")
    public static class SingleCardViewPopup_render {
        public static ExprEditor Instrument() { return patch; }
    }
    @SpirePatch(cls="com.megacrit.cardcrawl.vfx.campfire.CampfireSmithEffect", method="update")
    public static class CampfireSmithEffect_update {
        public static ExprEditor Instrument() { return patch; }
    }
    // Note: AbstractCard.makeStatEquivalentCopy() already copies stats

    // Events

    @SpirePatch(cls="com.megacrit.cardcrawl.events.shrines.AccursedBlacksmith", method="update")
    public static class AccursedBlacksmith_update {
        public static ExprEditor Instrument() { return patch; }
    }
    @SpirePatch(cls="com.megacrit.cardcrawl.events.shrines.UpgradeShrine", method="update")
    public static class UpgradeShrine_update {
        public static ExprEditor Instrument() { return patch; }
    }
    @SpirePatch(cls="com.megacrit.cardcrawl.events.thebottom.LivingWall", method="update")
    public static class LivingWall_update {
        public static ExprEditor Instrument() { return patch; }
    }
    @SpirePatch(cls="com.megacrit.cardcrawl.events.thebottom.ShiningLight", method="upgradeCards")
    public static class ShiningLight_upgradeCards {
        public static ExprEditor Instrument() { return patch; }
    }
    /*@SpirePatch(cls="com.megacrit.cardcrawl.events.thecity.BackToBasics", method="upgradeStrikeAndDefends")
    public static class BackToBasics_upgradeStrikeAndDefends {
        public static ExprEditor Instrument() { return patch; }
    }*/
    // Note: NeowReward is at start of game
    // Note: BackToBasics is patched separately.

    // Relics

    // Note: Don't care about eggs
    @SpirePatch(cls="com.megacrit.cardcrawl.relics.TinyHouse", method="onEquip")
    public static class TinyHouse_onEquip {
        public static ExprEditor Instrument() { return patch; }
    }
    @SpirePatch(cls="com.megacrit.cardcrawl.relics.WarPaint", method="onEquip")
    public static class WarPaint_onEquip {
        public static ExprEditor Instrument() { return patch; }
    }
    @SpirePatch(cls="com.megacrit.cardcrawl.relics.Whetstone", method="onEquip")
    public static class Whetstone_onEquip {
        public static ExprEditor Instrument() { return patch; }
    }
}

