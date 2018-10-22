package madsciencemod.powers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import madsciencemod.MadScienceMod;

public abstract class AbstractMadSciencePower extends AbstractPower {
    public static TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("madsciencemod/images/powers/powers.atlas"));

    public AbstractMadSciencePower(String id, String name, AbstractCreature owner) {
        this.ID = id;
        this.name = name;
        this.owner = owner;
        String filename = MadScienceMod.removeModId(id);
        this.region48 = atlas.findRegion("48/" + filename);
        this.region128 = atlas.findRegion("128/" + filename);
    }

    public AbstractMadSciencePower(String id, String name, AbstractCreature owner, int amount) {
        this(id, name, owner);
        this.amount = amount;
    }
}
