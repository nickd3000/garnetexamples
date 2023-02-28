package com.physmo.garnettest.invaders.components;

import com.physmo.garnet.Utils;
import com.physmo.garnet.regularfont.RegularFont;
import com.physmo.garnettest.invaders.GameData;
import com.physmo.garnettest.invaders.Resources;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.SceneManager;

public class ComponentHud extends Component {

    public static final String fontName = "/12x12Font.png";
    RegularFont regularFont;
    Resources resources;
    GameData gameData;

    public ComponentHud() {

    }

    @Override
    public void init() {
        gameData = SceneManager.getSharedContext().getObjectByType(GameData.class);
        resources = SceneManager.getSharedContext().getObjectByType(Resources.class);

        String fontFileName = Utils.getPathForResource(this, fontName);
        regularFont = new RegularFont(fontFileName, 12, 12);
    }

    @Override
    public void tick(double delta) {

    }

    @Override
    public void draw() {
        resources.bmfFontTexture.bind();
        resources.bmfFont.drawString(resources.bmfFontTexture, "Score:" + gameData.currentScore, 10, 10, 2);
        resources.bmfFont.drawString(resources.bmfFontTexture, "Lives:" + gameData.lives, 10 + 250, 10, 2);

        if (gameData.showGetReady) {
            resources.bmfFont.drawString(resources.bmfFontTexture, "Get Ready!", 640 / 9, 480 / 9, 4);
        }
    }
}
