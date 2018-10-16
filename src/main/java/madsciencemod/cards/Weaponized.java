package madsciencemod.cards;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ScreenUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import madsciencemod.MadScienceMod;

public class Weaponized extends AbstractMadScienceCard {
    public static final String ID = "Weaponized";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    public AbstractCard baseCard;

    public Weaponized(AbstractCard baseCard, int baseDamage) {
        super(ID, newName(baseCard.name), null, baseCard.cost, newDescription(baseCard.rawDescription), CardType.ATTACK, baseCard.color, baseCard.rarity, baseCard.target);
        // always have a target
        switch(target) {
            case SELF: case SELF_AND_ENEMY:
                target = CardTarget.SELF_AND_ENEMY;
            default:
                target = CardTarget.ENEMY;
        }
        this.baseCard = baseCard;
        this.baseDamage = baseDamage;
        this.initializeProperties();
        // Generate attack image
        AtlasRegion cardImg = (AtlasRegion)ReflectionHacks.getPrivate(baseCard, AbstractCard.class, "portrait");
        cardImg = getAttackImage(cardImg);
        ReflectionHacks.setPrivate(this, AbstractCard.class, "portrait", cardImg);
        if (baseCard instanceof CustomCard) {
            this.textureImg = ((CustomCard)baseCard).textureImg;
        }
    }

    private static String newName(String name) {
        return EXTENDED_DESCRIPTION[0] + name + EXTENDED_DESCRIPTION[1];
    }

    private static String newDescription(String rawDescription) {
        return EXTENDED_DESCRIPTION[2] + rawDescription + EXTENDED_DESCRIPTION[3];
    }

    private static HashMap<AtlasRegion, AtlasRegion> imgMap = new HashMap<>();
    private static Texture attackImg;
    private static AtlasRegion getAttackImage(AtlasRegion skillImg) {
        // See CustomCard
        if (imgMap.containsKey(skillImg)) {
            return imgMap.get(skillImg);
        } else {
            AtlasRegion texture = renderAttackImage(skillImg);
            imgMap.put(skillImg, texture);
            return texture;
        }
    }

    private static AtlasRegion renderAttackImage(AtlasRegion skillImg) {
        // we render the skill texture, but use alpha from an attack card
        int iwidth = 250, iheight = 190;
        FrameBuffer fbo = new FrameBuffer(Pixmap.Format.RGBA8888, iwidth, iheight, false);
        fbo.begin();
        //... clear the FBO color with transparent black ...
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f); //transparent black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clear the color buffer
        // set up batch and projection matrix
        SpriteBatch sb = new SpriteBatch();
        Matrix4 matrix = new Matrix4();
        matrix.setToOrtho(0.f,iwidth, iheight,0.f, 1.f,0.f);
        sb.setProjectionMatrix(matrix);
        // render the thing
        sb.begin();
        if (attackImg == null) {
            attackImg = new Texture(MadScienceMod.cardImage(Weaponized.ID));
        }
        sb.draw(attackImg, 0, 0);
        sb.setBlendFunction(GL20.GL_DST_ALPHA, GL20.GL_ZERO);
        sb.draw(skillImg, 0, 0);
        sb.end();
        sb.dispose();
        // get texture
        Pixmap pixmap = ScreenUtils.getFrameBufferPixmap(0,0, iwidth, iheight);
        Texture texture = new Texture(pixmap);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int tw = texture.getWidth();
        int th = texture.getHeight();
        // done
        fbo.end();
        pixmap.dispose();
        return new AtlasRegion(texture, 0, 0, tw, th);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Weaponized(baseCard.makeCopy(), baseDamage);
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        return new Weaponized(baseCard.makeStatEquivalentCopy(), baseDamage);
    }

    public void initializeProperties() {
        this.name = newName(baseCard.name);
        this.upgraded = baseCard.upgraded;
        this.timesUpgraded = baseCard.timesUpgraded;
        this.rawDescription = newDescription(baseCard.rawDescription);
        this.initializeTitle();
        this.initializeDescription();

        // note: baseDamage comes from weaponize
        this.baseBlock = baseCard.baseBlock;
        this.baseMagicNumber = baseCard.baseMagicNumber;
        this.block = baseCard.block;
        this.magicNumber = baseCard.magicNumber;
        this.upgradedBlock = baseCard.upgradedBlock;
        this.upgradedMagicNumber = baseCard.upgradedMagicNumber;
        this.upgradedCost = baseCard.upgradedCost;
        this.isBlockModified = baseCard.isBlockModified;
        this.isMagicNumberModified = baseCard.isMagicNumberModified;
        this.cost = baseCard.cost;
        this.costForTurn = baseCard.costForTurn;
        this.isCostModified = baseCard.isCostModified;
        this.isCostModifiedForTurn = baseCard.isCostModifiedForTurn;
        this.inBottleLightning = baseCard.inBottleLightning;
        this.inBottleFlame = baseCard.inBottleFlame;
        this.inBottleTornado = baseCard.inBottleTornado;
        this.isSeen = baseCard.isSeen;
        this.isLocked = baseCard.isLocked;
        this.misc = baseCard.misc;
        this.exhaust = baseCard.exhaust;
        this.exhaustOnUseOnce = baseCard.exhaustOnUseOnce;
        this.freeToPlayOnce = baseCard.freeToPlayOnce;
        this.isInnate = baseCard.isInnate;
        this.isEthereal = baseCard.isEthereal;
        if (baseCard instanceof AbstractMadScienceCard) {
            this.fuelCost = ((AbstractMadScienceCard)baseCard).fuelCost;
        }
    }

    // Some actions, notably MadnessAction and EnlightnmentAction directly modify card.costForTurn or card.cost
    // this propagates the cost to the baseCard, from which we will later copy it.
    // We have to copy cost from the baseCard for cards that modify their own cost like Eviscerate
    void updateBaseCost() {
        baseCard.isCostModified = this.isCostModified;
        baseCard.isCostModifiedForTurn = this.isCostModifiedForTurn;
        baseCard.costForTurn = this.costForTurn;
        baseCard.cost = this.cost;
    }

    @Override
    public boolean cardPlayable(AbstractMonster m) {
        updateBaseCost();
        return baseCard.cardPlayable(m);
    }

    @Override
    public boolean hasEnoughEnergy() {
        updateBaseCost();
        return baseCard.hasEnoughEnergy();
    }

    @Override
    public void tookDamage() {
        baseCard.tookDamage();
    }

    @Override
    public void didDiscard() {
        baseCard.didDiscard();
    }

    @Override
    public boolean canPlay(AbstractCard card) {
        updateBaseCost();
        return baseCard.canPlay(card);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        updateBaseCost();
        return baseCard.canUse(p,m);
    }

    @Override
    public void onMoveToDiscard() {
        baseCard.onMoveToDiscard();
    }

    @Override
    public void triggerWhenDrawn() {
        baseCard.triggerWhenDrawn();
    }

    @Override
    public void triggerWhenCopied() {
        baseCard.triggerWhenCopied();
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        super.triggerOnEndOfPlayerTurn();
        // only used for ethereal
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        baseCard.triggerOnEndOfTurnForPlayingCard();
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        baseCard.triggerOnOtherCardPlayed(c);
    }

    @Override
    public void triggerOnGainEnergy(int e, boolean dueToCard) {
        baseCard.triggerOnGainEnergy(e,dueToCard);
    }

    @Override
    public void triggerOnManualDiscard() {
        baseCard.triggerOnManualDiscard();
    }

    @Override
    public void triggerOnCardPlayed(AbstractCard cardPlayed) {
        baseCard.triggerOnCardPlayed(cardPlayed);
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        baseCard.onPlayCard(c,m);
    }

    @Override
    public void atTurnStart() {
        updateBaseCost();
        baseCard.atTurnStart();
    }

    @Override
    public void triggerOnExhaust() {
        updateBaseCost();
        baseCard.triggerOnExhaust();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        updateBaseCost();
        baseCard.applyPowers();
        initializeProperties();
    }

    // Note: calculateDamageDisplay just calls calculateCardDamage

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        updateBaseCost();
        baseCard.calculateCardDamage(mo);
        initializeProperties();
    }

    @Override
    public void updateCost(int amt) {
        updateBaseCost();
        baseCard.updateCost(amt);
        initializeProperties();
    }

    @Override
    public void setCostForTurn(int amt) {
        updateBaseCost();
        baseCard.setCostForTurn(amt);
        initializeProperties();
    }

    @Override
    public void modifyCostForTurn(int amt) {
        updateBaseCost();
        baseCard.modifyCostForTurn(amt);
        initializeProperties();
    }

    @Override
    public void modifyCostForCombat(int amt) {
        updateBaseCost();
        baseCard.modifyCostForCombat(amt);
        initializeProperties();
    }

    @Override
    public void resetAttributes() {
        updateBaseCost();
        baseCard.resetAttributes();
        initializeProperties();
    }

    @Override
    public void clearPowers() {
        updateBaseCost();
        baseCard.clearPowers();
        initializeProperties();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        baseCard.use(p,m);
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            updateBaseCost();
            baseCard.upgrade();
            initializeProperties();
        }
    }

    @Override
    public boolean canUpgrade() {
        return baseCard.canUpgrade();
    }
}
