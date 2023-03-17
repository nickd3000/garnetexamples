package com.physmo.garnettest.invaders.components;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.Utils;
import com.physmo.garnet.regularfont.RegularFont;
import com.physmo.garnet.spritebatch.TileSheet;
import com.physmo.garnettest.invaders.GameData;
import com.physmo.garnettest.invaders.Resources;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.SceneManager;
import com.physmo.garnettoolkit.color.Color;

public class ComponentHud extends Component {

    //public static final String fontName = "/12x12Font.png";
    public static final String fontName = "/5x5_0_b.png";

    RegularFont regularFont;
    Resources resources;
    GameData gameData;
    Garnet garnet;
    TileSheet tileSheet;
    int textColor = Color.YELLOW.toInt();

    public ComponentHud() {

    }

    @Override
    public void init() {
        gameData = SceneManager.getSharedContext().getObjectByType(GameData.class);
        resources = SceneManager.getSharedContext().getObjectByType(Resources.class);
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);

        String fontFileName = Utils.getPathForResource(this, fontName);
        regularFont = new RegularFont(fontFileName, 12, 12);
        tileSheet = parent.getContext().getObjectByType(TileSheet.class);
    }

    @Override
    public void tick(double delta) {

    }

    @Override
    public void draw() {

        garnet.getGraphics().setColor(textColor);
        garnet.getGraphics().setScale(2);
        resources.bmfFont.drawString(garnet.getGraphics(), resources.bmfFontTexture, "Score:" + gameData.currentScore, 10, 10);
        resources.bmfFont.drawString(garnet.getGraphics(), resources.bmfFontTexture, "Lives:" + gameData.lives, 10 + 250, 10);

        if (gameData.showGetReady) {
            int scale = 4;
            garnet.getGraphics().setScale(scale);
            int stringWidth = resources.bmfFont.getStringWidth("Get Ready!");
            int windowWidth = garnet.getDisplay().getWindowWidth() / scale;
            int textX = (windowWidth / 2) - (stringWidth / 2);


            garnet.getGraphics().setColor(Color.RED.toInt());
            resources.bmfFont.drawString(garnet.getGraphics(), resources.bmfFontTexture, "Get Ready!", textX, 480 / 9);
        }
    }
}
