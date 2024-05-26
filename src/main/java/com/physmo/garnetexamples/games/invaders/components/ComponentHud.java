package com.physmo.garnetexamples.games.invaders.components;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.text.BitmapFont;
import com.physmo.garnetexamples.games.invaders.GameData;
import com.physmo.garnetexamples.games.invaders.Resources;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.color.ColorUtils;
import com.physmo.garnettoolkit.scene.SceneManager;
import com.physmo.garnettoolkit.simplecollision.CollisionSystem;

public class ComponentHud extends Component {

    final int BANNER_SCALE = 3;
    final int SCORE_SCALE = 1;
    Resources resources;
    GameData gameData;
    Garnet garnet;
    ComponentGameLogic gameLogic;
    CollisionSystem collisionSystem;
    BitmapFont bitmapFont;

    int textColor = ColorUtils.YELLOW;
    double textFlash = 0;
    double overlayScroll = 0;


    public ComponentHud() {

    }

    @Override
    public void init() {
        gameData = SceneManager.getSharedContext().getObjectByType(GameData.class);
        resources = SceneManager.getSharedContext().getObjectByType(Resources.class);
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);

        gameLogic = parent.getContext().getComponent(ComponentGameLogic.class);
        collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);
        bitmapFont = resources.getBitmapFont();

    }

    @Override
    public void tick(double delta) {
        textFlash += delta;
        if (textFlash > 10) textFlash -= 10;

        overlayScroll += delta * 1.5;
        if (overlayScroll > 1) overlayScroll -= 1;
    }

    @Override
    public void draw() {

        garnet.getGraphics().setColor(textColor);
        bitmapFont.setScale(SCORE_SCALE);
        bitmapFont.drawText(garnet.getGraphics(), "Score:" + gameData.currentScore, 10, 10);
        bitmapFont.drawText(garnet.getGraphics(), "Lives:" + gameData.lives, 10 + 250, 10);


        String textGetReady = "Get Ready!";
        String textGameOver = "GAME OVER";

        if (gameLogic.showGetReady()) {
            drawBanner(textGetReady, 6);
            drawOverlay();
        }
        if (gameLogic.showGameOver()) {
            drawBanner(textGameOver, 6);
            drawOverlay();
        }
        if (gameLogic.showLevelComplete()) {
            drawBanner("Level complete!", 5);
            drawOverlay();
        }

        garnet.getGraphics().setColor(textColor);
        double fps = garnet.getGameClock().getFps();
        double lps = garnet.getGameClock().getLps();
        String clock = "fps:" + fps + "  lps:" + lps + " objects:" + parent.getContext().getObjectCount();
        clock += " colliders:" + collisionSystem.getSize();

        bitmapFont.setScale(0.7);
        bitmapFont.drawText(garnet.getGraphics(), clock, 10, 220);

    }

    private void drawBanner(String text, double scale) {
        Graphics graphics = garnet.getGraphics();

        int stringWidth = resources.getBitmapFont().getStringWidth(text);
        int windowWidth = (int) (garnet.getDisplay().getWindowWidth() / scale);
        int windowHeight = (int) (garnet.getDisplay().getWindowHeight() / scale);
        int textX = (windowWidth / 2) - (stringWidth / 2);
        int textY = (windowHeight / 2) - (16 / 2);

        if (((int) (textFlash * 10)) % 2 == 0) graphics.setColor(ColorUtils.RED);
        else graphics.setColor(ColorUtils.BLUE);

        bitmapFont.setScale(BANNER_SCALE);
        bitmapFont.drawText(graphics, text, textX, textY);

    }

    // draw some big transparent invader sprites scrolling across the screen
    private void drawOverlay() {
        Graphics graphics = garnet.getGraphics();

        int scale = 2;
        int numY = 20;
        int numX = 20;
        int ySpan = (garnet.getDisplay().getWindowHeight() / scale) / numY;
        int xSpan = (garnet.getDisplay().getWindowWidth() / scale) / numX;

        graphics.setColor(ColorUtils.asRGBA(0.5F, 1, 0.5f, 0.1f));
        for (int y = -1; y <= numY; y++) {
            for (int x = -1; x <= numX; x++) {
                int offset = (int) (overlayScroll * ySpan);
                graphics.drawImage(resources.getSpriteTilesheet(), (x * xSpan) - (xSpan / 2), y * ySpan + offset, 2, 2);
            }
        }
    }
}
