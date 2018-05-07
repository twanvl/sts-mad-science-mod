package inventormod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import inventormod.cards.AbstractInventorCard;
import inventormod.powers.FuelPower;

@SpirePatch(cls="com.megacrit.cardcrawl.characters.AbstractPlayer", method="useCard")
public class UseCardPatch {
    //public static IdentityHashMap<AbstactCard> cardsThatCostFuelUntilPlayed;
    public static void Prefix(Object _self, Object _c, Object _monster, int energyOnUse) {
        if (_c instanceof AbstractInventorCard) {
            AbstractInventorCard c = (AbstractInventorCard)_c;
            if (!c.freeToPlayOnce && c.fuelCostForTurn() > 0) {
                FuelPower.spendFuel(c.fuelCostForTurn());
            }
            if (c.costsFuelThisTurn) {
                // in AbstractPlayer.useCard, we don't want to pay energy
                c.hackNotToPayCost = c.costForTurn;
                c.costForTurn = 0;
            }
        }
    }
    public static void Postfix(Object _self, Object _c, Object _monster, int energyOnUse) {
        if (_c instanceof AbstractInventorCard) {
            AbstractInventorCard c = (AbstractInventorCard)_c;
            if (c.costsFuelThisTurn) {
                c.costForTurn = c.hackNotToPayCost;
                c.costsFuelThisTurn = false; // Only once
            }
        }
    }
    /*
    Alternative
    cm.insertBefore(
        "if (!c.freeToPlayOnce && c.fuelCostForTurn() > 0) {
            FuelPower.spendFuel(c.fuelCostForTurn());
        }"
    )
    cm.instrument(new ExprEditor(){
        public void edit(MethodCall m) throws CannotCompileException {
            if (m.getClassName().equals("EnergyManager") && m.getMethodName().equals("use")) {
                m.replace(
                    "if (c.costsFuelThisTurn) {
                        FuelPower.spendFuel(c.fuelCostForTurn());
                    } else {
                        $_ = $proceed($$);
                    }");
            }
        }
    })
    */
}