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
    public static final String fontName = "5x5_0_b.png";

    RegularFont regularFont;
    Resources resources;
    GameData gameData;
    Garnet garnet;
    TileSheet tileSheet;
    int textColor = Color.YELLOW.toInt();
    double textFlash = 0;
    ComponentGameLogic gameLogic;

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
        gameLogic = parent.getContext().getComponent(ComponentGameLogic.class);
    }

    @Override
    public void tick(double delta) {
        textFlash += delta;
        if (textFlash > 10) textFlash -= 10;
    }

    @Override
    public void draw() {

        garnet.getGraphics().setColor(textColor);
        garnet.getGraphics().setScale(2);
        resources.bmfFont.drawString(garnet.getGraphics(), resources.bmfFontTexture, "Score:" + gameData.currentScore, 10, 10);
        resources.bmfFont.drawString(garnet.getGraphics(), resources.bmfFontTexture, "Lives:" + gameData.lives, 10 + 250, 10);

        String textGetReady = "Get Ready!";

        if (gameLogic.showGetReady()) {
            int scale = 4;
            double prevScale = garnet.getGraphics().getScale();
            garnet.getGraphics().setScale(scale);
            int stringWidth = resources.bmfFont.getStringWidth(textGetReady);
            int windowWidth = garnet.getDisplay().getWindowWidth() / scale;
            int textX = (windowWidth / 2) - (stringWidth / 2);


            if (((int) (textFlash * 10)) % 2 == 0)
                garnet.getGraphics().setColor(Color.RED.toInt());
            else
                garnet.getGraphics().setColor(Color.BLUE.toInt());

            resources.bmfFont.drawString(garnet.getGraphics(), resources.bmfFontTexture, textGetReady, textX, 480 / 9);
            garnet.getGraphics().setScale(prevScale);
        }
    }
}
