package com.physmo.garnetexamples.games.invaders;

import com.physmo.garnet.FileUtils;
import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Texture;
import com.physmo.garnet.graphics.TileSheet;
import com.physmo.garnet.text.BitmapFont;
import com.physmo.garnet.text.RegularFont;

import java.io.IOException;

public class Resources {

    public static final String bitmapFontImagePath = "bitmapfonts/LucidaConsole16.png"; //Utils.getPathForResource(this, "LucidaConsole16.png");
    public static final String bitmapFontSpecFile = "bitmapfonts/LucidaConsole16.fnt"; //Utils.getPathForResource(this, "LucidaConsole16.fnt");
    public static final String regularFontImagePath = "regularfonts/12x12Font.png";
    public static final String spriteSheetFileName = "space.png";
    public int soundIdLaser;
    public int soundIdExplosion;
    public int soundIdShieldHit;
    public int soundIdEnemyFire;
    BitmapFont bitmapFont;
    RegularFont regularFont;
    Garnet garnet;
    Texture spriteTexture;
    TileSheet spriteTileSheet;

    public Resources(Garnet garnet) {
        this.garnet = garnet;
    }

    public BitmapFont getBitmapFont() {
        return bitmapFont;
    }

    public RegularFont getRegularFont() {
        return regularFont;
    }

    public TileSheet getSpriteTilesheet() {
        return spriteTileSheet;
    }

    public Resources init() {

        spriteTexture = Texture.loadTexture(spriteSheetFileName);
        garnet.getGraphics().addTexture(spriteTexture);
        spriteTexture.setFilter(true);
        spriteTileSheet = new TileSheet(spriteTexture, 16, 16);

        try {
            bitmapFont = new BitmapFont(bitmapFontImagePath, bitmapFontSpecFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Regular font
        regularFont = new RegularFont(regularFontImagePath, 12, 12);

        // Sound
        soundIdLaser = loadSound("sounds/laserShoot-3.wav");
        soundIdExplosion = loadSound("sounds/explosion.wav");
        soundIdShieldHit = loadSound("sounds/pulse.wav");
        soundIdEnemyFire = loadSound("sounds/swish.wav");

        return this;
    }

    public int loadSound(String path) {
        return garnet.getSound().loadSound(FileUtils.getPathForResource(this, path));
    }

}
