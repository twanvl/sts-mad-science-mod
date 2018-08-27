package madsciencemod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import madsciencemod.cards.Postpone;

@SpirePatch(clz=com.megacrit.cardcrawl.actions.utility.UseCardAction.class, method=SpirePatch.CONSTRUCTOR, paramtypez={AbstractCard.class, AbstractCreature.class})
public class UseCardActionPatch {
    public static void Postfix(Object _self, Object card, Object target) {
        if (card instanceof Postpone) {
            ((UseCardAction)_self).reboundCard = true;
        }
    }
}
