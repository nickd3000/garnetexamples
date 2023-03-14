package com.physmo.garnettest.invaders.scenes;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.Utils;
import com.physmo.garnet.input.Input;
import com.physmo.garnettest.invaders.Resources;
import com.physmo.garnettoolkit.Scene;
import com.physmo.garnettoolkit.SceneManager;
import com.physmo.garnettoolkit.color.Color;

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
        if (garnet.getInput().isPressed(Input.VirtualButton.FIRE1)) {
            SceneManager.setActiveScene("game");
        }
    }

    @Override
    public void draw() {
        garnet.setDrawModeWireframe();
        garnet.getDisplay().drawLine(-100, -100, 100, 100);

        garnet.setDrawModeNormal2D();

        garnet.setDrawColor(textColor);
        resources.bmfFontTexture.bind();

        resources.bmfFont.drawString(resources.bmfFontTexture, "Garnet Example Project", 20, 20, 2);
        resources.bmfFont.drawString(resources.bmfFontTexture, "Invaders", 20, 100, 3);

        garnet.setDrawColor(Color.WHITE.toInt());


    }

    @Override
    public void onMakeActive() {

    }

    @Override
    public void onMakeInactive() {

    }
}
