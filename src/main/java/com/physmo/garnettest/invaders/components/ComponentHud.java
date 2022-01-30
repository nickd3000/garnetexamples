package com.physmo.garnettest.invaders.components;

import com.physmo.garnet.Utils;
import com.physmo.garnet.entity.Component;
import com.physmo.garnet.regularfont.RegularFont;
import com.physmo.garnettest.invaders.GameData;
import com.physmo.garnettest.invaders.Resources;

public class ComponentHud extends Component {

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
    public void tick(double delta) {

    }

    @Override
    public void draw() {
        GameData gameData = (GameData) parent.garnet.getGlobalObject("game_data");

        resources.bmfFontTexture.bind();
        resources.bmfFont.drawString(resources.bmfFontTexture, "Score:" + gameData.currentScore, 10, 10, 2);
        resources.bmfFont.drawString(resources.bmfFontTexture, "Lives:" + gameData.lives, 10 + 250, 10, 2);

        if (gameData.showGetReady) {
            resources.bmfFont.drawString(resources.bmfFontTexture, "Get Ready!", 640 / 9, 480 / 9, 4);
        }

    }
}
