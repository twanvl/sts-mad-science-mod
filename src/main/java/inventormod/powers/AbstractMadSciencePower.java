package inventormod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import inventormod.MadScienceMod;

public abstract class AbstractMadSciencePower extends AbstractPower {
    public AbstractMadSciencePower(String id, String name, AbstractCreature owner) {
        this.ID = id;
        this.name = name;
        this.owner = owner;
        this.img = ImageMaster.loadImage(MadScienceMod.powerImage(id));
    }

    public AbstractMadSciencePower(String id, String name, AbstractCreature owner, int amount) {
        this(id, name, owner);
        this.amount = amount;
    }
}
