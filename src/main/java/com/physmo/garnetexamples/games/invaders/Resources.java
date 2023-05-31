package com.physmo.garnetexamples.games.invaders;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.Utils;
import com.physmo.garnet.bitmapfont.BitmapFont;
import com.physmo.garnet.regularfont.RegularFont;

import java.io.IOException;

public class Resources {
    public int soundLaserId;
    public int soundExplosionId;
    public int soundShieldHitId;
    public int soundEnemyFireId;
    BitmapFont bitmapFont;
    RegularFont regularFont;
    Garnet garnet;


    public Resources(Garnet garnet) {
        this.garnet = garnet;
    }

    public BitmapFont getBitmapFont() {
        return bitmapFont;
    }

    public RegularFont getRegularFont() {
        return regularFont;
    }

    public Resources init() {

        String fontImagePath = "bitmapfonts/LucidaConsole16.png"; //Utils.getPathForResource(this, "LucidaConsole16.png");
        String fontSpecFile = "bitmapfonts/LucidaConsole16.fnt"; //Utils.getPathForResource(this, "LucidaConsole16.fnt");

        String pathForResource = fontSpecFile;
        try {
            bitmapFont = new BitmapFont(fontImagePath, fontSpecFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Regular font
        String fontName = "regularfonts/12x12Font.png";
        //String fontFileName = Utils.getPathForResource(this, fontName);

        regularFont = new RegularFont(fontName, 12, 12);


        // Sound
        soundLaserId = loadSound("sounds/laserShoot-3.wav");
        soundExplosionId = loadSound("sounds/explosion.wav");
        soundShieldHitId = loadSound("sounds/pulse.wav");
        soundEnemyFireId = loadSound("sounds/swish.wav");

        return this;
    }

    public int loadSound(String path) {
        return garnet.getSound().
                loadSound(Utils.getPathForResource(this, path));
    }

}
