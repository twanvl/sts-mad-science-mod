package madsciencemod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;

import madsciencemod.cards.Postpone;

@SpirePatch(cls="com.megacrit.cardcrawl.actions.utility.UseCardAction", method="ctor")
public class UseCardActionPatch {
    public static void Postfix(Object _self, Object card, Object target) {
        if (card instanceof Postpone) {
            ((UseCardAction)_self).reboundCard = true;
        }
    }
}
