package madsciencemod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import madsciencemod.powers.FuelPower;

@SpirePatch(clz=com.megacrit.cardcrawl.characters.AbstractPlayer.class, method="preBattlePrep")
public class PreBattlePrepPatch {
    public static void Postfix(Object __obj_instance) {
        FuelPower.atPreBattle();
    }
}
