package com.physmo.garnetexamples.games.invaders.scenes;

import com.physmo.garnet.ColorUtils;
import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.input.InputAction;
import com.physmo.garnet.toolkit.color.ColorSupplierLinear;
import com.physmo.garnet.toolkit.scene.Scene;
import com.physmo.garnet.toolkit.scene.SceneManager;
import com.physmo.garnetexamples.games.invaders.Resources;

public class SceneMenu extends Scene {

    Resources resources;
    Garnet garnet;
    int textColor = ColorUtils.rgb(255, 255, 255, 255);
    ColorSupplierLinear colorSupplierLinear;

    public SceneMenu(String name) {
        super(name);
    }

    double flashTimer = 0;

    @Override
    public void init() {
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        resources = SceneManager.getSharedContext().getObjectByType(Resources.class);
        int[] colorList = new int[]{ColorUtils.SUNSET_ORANGE, ColorUtils.WINTER_BLUE, ColorUtils.VIOLET};
        colorSupplierLinear = new ColorSupplierLinear(colorList);
    }

    @Override
    public void tick(double delta) {
        if (garnet.getInput().isActionKeyPressed(InputAction.FIRE1)) {
            SceneManager.setActiveScene("game");
        }
        flashTimer += delta;
    }

    @Override
    public void draw(Graphics g) {
        garnet.getGraphics().setColor(colorSupplierLinear.getColor(flashTimer));
        resources.getBitmapFont().setScale(4);

        resources.getBitmapFont().drawText(garnet.getGraphics(), "Garnet Example Project", 20, 20);
        garnet.getGraphics().setColor(colorSupplierLinear.getColor(Math.random()));
        resources.getBitmapFont().drawText(garnet.getGraphics(), "Invaders", 20, 100);
    }

    @Override
    public void onMakeActive() {

    }

    @Override
    public void onMakeInactive() {

    }
}
