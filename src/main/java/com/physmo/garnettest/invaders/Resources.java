package com.physmo.garnettest.invaders;

import com.physmo.garnet.Texture;
import com.physmo.garnet.Utils;
import com.physmo.garnet.bitmapfont.BMFFont;

import java.io.IOException;

public class Resources {

    public BMFFont bmfFont;
    public Texture bmfFontTexture;

    public Resources init() {
        bmfFontTexture = Texture.loadTexture(Utils.getPathForResource(this, "/ptmono16_0.png"));
        bmfFont = new BMFFont();
        String pathForResource = Utils.getPathForResource(this, "/ptmono16.fnt");
        try {
            bmfFont.init(pathForResource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

}
