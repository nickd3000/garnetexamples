package com.physmo.garnettest.invaders.renderers;

import com.physmo.garnet.Utils;
import com.physmo.garnet.entity.RenderComponent;
import com.physmo.garnet.regularfont.RegularFont;
import com.physmo.garnettest.invaders.GameData;
import com.physmo.garnettest.invaders.Resources;

public class RenderComponentHud extends RenderComponent {

    public static final String fontName = "/12x12Font.png";
    RegularFont regularFont;
    Resources resources;

    @Override
    public void init() {
        String fontFileName = Utils.getPathForResource(this, fontName);
        regularFont = new RegularFont(fontFileName, 12, 12);
        resources = (Resources) parent.garnet.getGlobalObject("Resources");
    }

    @Override
    public void draw() {
        GameData gameData = (GameData) parent.garnet.getGlobalObject("game_data");

        resources.bmfFontTexture.bind();
        resources.bmfFont.drawString(resources.bmfFontTexture, "Score:" + gameData.currentScore, 10, 10, 2);
        resources.bmfFont.drawString(resources.bmfFontTexture, "Lives:" + gameData.lives, 10 + 250, 10, 2);

    }
}
