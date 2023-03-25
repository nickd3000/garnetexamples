package com.physmo.garnetexamples.invaders;

import com.physmo.garnet.Utils;
import com.physmo.garnet.bitmapfont.BitmapFont;
import com.physmo.garnet.regularfont.RegularFont;

import java.io.IOException;

public class Resources {


    BitmapFont bitmapFont;
    RegularFont regularFont;

    public BitmapFont getBitmapFont() {
        return bitmapFont;
    }

    public void setBitmapFont(BitmapFont bitmapFont) {
        this.bitmapFont = bitmapFont;
    }

    public RegularFont getRegularFont() {
        return regularFont;
    }

    public void setRegularFont(RegularFont regularFont) {
        this.regularFont = regularFont;
    }

    public Resources init() {

        String fontImagePath = Utils.getPathForResource(this, "ptmono16_0.png");
        String fontSpecFile = Utils.getPathForResource(this, "ptmono16.fnt");

        String pathForResource = fontSpecFile;
        try {
            bitmapFont = new BitmapFont(fontImagePath, fontSpecFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Regular font
        String fontName = "12x12Font.png";
        String fontFileName = Utils.getPathForResource(this, fontName);
        regularFont = new RegularFont(fontFileName, 12, 12);

        return this;
    }

}
