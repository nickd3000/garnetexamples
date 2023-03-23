package com.physmo.garnetexamples.invaders.components;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.spritebatch.TileSheet;
import com.physmo.garnetexamples.invaders.GameData;
import com.physmo.garnetexamples.invaders.Resources;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.SceneManager;
import com.physmo.garnettoolkit.color.Color;
import com.physmo.garnettoolkit.simplecollision.CollisionSystem;

public class ComponentHud extends Component {

    Resources resources;
    GameData gameData;
    Garnet garnet;
    TileSheet tileSheet;
    int textColor = Color.YELLOW.toInt();
    double textFlash = 0;
    ComponentGameLogic gameLogic;
    CollisionSystem collisionSystem;
    public ComponentHud() {

    }

    @Override
    public void init() {
        gameData = SceneManager.getSharedContext().getObjectByType(GameData.class);
        resources = SceneManager.getSharedContext().getObjectByType(Resources.class);
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);

        tileSheet = parent.getContext().getObjectByType(TileSheet.class);
        gameLogic = parent.getContext().getComponent(ComponentGameLogic.class);
        collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);
    }

    @Override
    public void tick(double delta) {
        textFlash += delta;
        if (textFlash > 10) textFlash -= 10;
    }

    @Override
    public void draw() {

        double prevScale = garnet.getGraphics().getScale();

        garnet.getGraphics().setColor(textColor);
        garnet.getGraphics().setScale(2);
        resources.bmfFont.drawString(garnet.getGraphics(), resources.bmfFontTexture, "Score:" + gameData.currentScore, 10, 10);
        resources.bmfFont.drawString(garnet.getGraphics(), resources.bmfFontTexture, "Lives:" + gameData.lives, 10 + 250, 10);


        String textGetReady = "Get Ready!";
        String textGameOver = "GAME OVER";
        if (gameLogic.showGetReady()) {
            int scale = 4;

            garnet.getGraphics().setScale(scale);
            int stringWidth = resources.bmfFont.getStringWidth(textGetReady);
            int windowWidth = garnet.getDisplay().getWindowWidth() / scale;
            int textX = (windowWidth / 2) - (stringWidth / 2);


            if (((int) (textFlash * 10)) % 2 == 0) garnet.getGraphics().setColor(Color.RED.toInt());
            else garnet.getGraphics().setColor(Color.BLUE.toInt());

            resources.bmfFont.drawString(garnet.getGraphics(), resources.bmfFontTexture, textGetReady, textX, 480 / 9);
            garnet.getGraphics().setScale(prevScale);
        }

        if (gameLogic.showGameOver()) {
            int scale = 4;

            garnet.getGraphics().setScale(scale);
            int stringWidth = resources.bmfFont.getStringWidth(textGameOver);
            int windowWidth = garnet.getDisplay().getWindowWidth() / scale;
            int textX = (windowWidth / 2) - (stringWidth / 2);


            if (((int) (textFlash * 10)) % 2 == 0) garnet.getGraphics().setColor(Color.RED.toInt());
            else garnet.getGraphics().setColor(Color.BLUE.toInt());

            resources.bmfFont.drawString(garnet.getGraphics(), resources.bmfFontTexture, textGameOver, textX, 480 / 9);
            garnet.getGraphics().setScale(prevScale);
        }

        garnet.getGraphics().setScale(1);
        garnet.getGraphics().setColor(textColor);
        double fps = garnet.getGameClock().getFps();
        double lps = garnet.getGameClock().getLps();
        String clock = "fps:" + fps + "  lps:" + lps + " objects:" + parent.getContext().getObjectCount();
        clock += " colliders:" + collisionSystem.getSize();
        garnet.getGraphics().setScale(1);
        resources.bmfFont.drawString(garnet.getGraphics(), resources.bmfFontTexture, clock, 10, 26 + 20);

        garnet.getGraphics().setScale(prevScale);
    }
}
