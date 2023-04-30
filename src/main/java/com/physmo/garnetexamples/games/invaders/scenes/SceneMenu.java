package com.physmo.garnetexamples.games.invaders.scenes;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.Utils;
import com.physmo.garnet.input.InputAction;
import com.physmo.garnetexamples.games.invaders.Resources;
import com.physmo.garnettoolkit.Scene;
import com.physmo.garnettoolkit.SceneManager;

public class SceneMenu extends Scene {

    Resources resources;
    Garnet garnet;
    int textColor = Utils.rgb(255, 255, 255, 255);

    public SceneMenu(String name) {
        super(name);
    }

    @Override
    public void init() {
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        resources = SceneManager.getSharedContext().getObjectByType(Resources.class);
    }

    @Override
    public void tick(double delta) {
        if (garnet.getInput().isPressed(InputAction.FIRE1)) {
            SceneManager.setActiveScene("game");
        }
    }

    @Override
    public void draw() {
        garnet.getGraphics().setColor(textColor);
        garnet.getGraphics().setScale(2);
        resources.getBitmapFont().drawString(garnet.getGraphics(), "Garnet Example Project", 20, 20);
        resources.getBitmapFont().drawString(garnet.getGraphics(), "Invaders", 20, 100);
    }

    @Override
    public void onMakeActive() {

    }

    @Override
    public void onMakeInactive() {

    }
}
