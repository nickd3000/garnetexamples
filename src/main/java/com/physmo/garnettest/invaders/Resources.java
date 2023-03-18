package com.physmo.garnettest.invaders;

import com.physmo.garnet.Texture;
import com.physmo.garnet.Utils;
import com.physmo.garnet.bitmapfont.BMFFont;
import com.physmo.garnet.regularfont.RegularFont;

import java.io.IOException;

public class Resources {

    public BMFFont bmfFont;
    public Texture bmfFontTexture;
    public RegularFont regularFont;

    public Resources init() {
        bmfFontTexture = Texture.loadTexture(Utils.getPathForResource(this, "ptmono16_0.png"));
        bmfFont = new BMFFont();
        String pathForResource = Utils.getPathForResource(this, "ptmono16.fnt");
        try {
            bmfFont.init(pathForResource);
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
