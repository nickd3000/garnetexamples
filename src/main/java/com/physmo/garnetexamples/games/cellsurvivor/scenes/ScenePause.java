package com.physmo.garnetexamples.games.cellsurvivor.scenes;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.input.InputAction;
import com.physmo.garnet.input.InputKeys;
import com.physmo.garnet.text.RegularFont;
import com.physmo.garnet.toolkit.scene.Scene;
import com.physmo.garnet.toolkit.scene.SceneManager;
import com.physmo.garnetexamples.games.cellsurvivor.Constants;
import com.physmo.garnetexamples.games.cellsurvivor.Resources;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentPlayerCapabilities;
import com.physmo.garnetexamples.games.cellsurvivor.components.PlayerCapability;

public class ScenePause extends Scene {

    Garnet garnet;
    Resources resources;
    RegularFont regularFont;
    ComponentPlayerCapabilities playerCapabilities;

    public ScenePause(String name) {
        super(name);
    }

    @Override
    public void init() {
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        resources = SceneManager.getSharedContext().getObjectByType(Resources.class);

        regularFont = resources.getRegularFont();
    }

    @Override
    public void tick(double delta) {
        // Handle pause
        if (garnet.getInput().isActionKeyPressed(InputAction.MENU)) {
            //ScenePause scenePause = SceneManager.getSharedContext().getObjectByType(ScenePause.class);
            SceneManager.popSubScene("pause");

        }

        if (garnet.getInput().getKeyboard().isKeyFirstPress(InputKeys.KEY_P)) {
            SceneManager.popSubScene("pause");
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setDrawOrder(Constants.DRAW_ORDER_PAUSE_BACKGROUND);
        g.setActiveViewport(Constants.overlayViewportId);
        int[] bufferSize = garnet.getDisplay().getBufferSize();
        g.filledRect(10, 10, 200, 150);
        drawPlayerDetails(g);
    }

    @Override
    public void onMakeActive() {

    }

    @Override
    public void onMakeInactive() {

    }

    public void drawPlayerDetails(Graphics g) {
        g.setColor(0x005500ff);
        //g.setDrawOrder(Constants.DRAW_ORDER_PAUSE_BACKGROUND+1);
        regularFont.drawText(g, "hello", 100, 100);

        drawCapability(g, PlayerCapability.PICKUP_RADIUS, "Pickup Radius", 30, 50);
        drawCapability(g, PlayerCapability.PROJECTILE_MULTIPLIER, "Projectile Multiplier", 30, 65);
        drawCapability(g, PlayerCapability.PROJECTILE_POWER, "Projectile Power", 30, 80);
        drawCapability(g, PlayerCapability.PROJECTILE_RATE, "Projectile Rate", 30, 95);
        drawCapability(g, PlayerCapability.PROJECTILE_SPEED, "Projectile Speed", 30, 110);
    }

    public void drawCapability(Graphics g, PlayerCapability id, String description, int x, int y) {
        int val = playerCapabilities.getLevel(id);
        regularFont.drawText(g, description + ": " + val, x, y);
    }

    public void setPlayerCapabilities(ComponentPlayerCapabilities playerCapabilities) {

        this.playerCapabilities = playerCapabilities;

    }


}
