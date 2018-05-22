package madsciencemod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import madsciencemod.powers.FuelPower;

@SpirePatch(cls="com.megacrit.cardcrawl.characters.AbstractPlayer", method="preBattlePrep")
public class PreBattlePrepPatch {
    public static void Postfix(Object __obj_instance) {
        FuelPower.atPreBattle();
    }
}
