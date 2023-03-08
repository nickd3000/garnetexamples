package com.physmo.garnettest.invaders.scenes;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.Utils;
import com.physmo.garnet.input.Input;
import com.physmo.garnet.regularfont.RegularFont;
import com.physmo.garnettest.invaders.Resources;
import com.physmo.garnettoolkit.Scene;
import com.physmo.garnettoolkit.SceneManager;

public class SceneMenu extends Scene {

    public static final String fontName = "/12x12Font.png";
    RegularFont regularFont;

    Resources resources;
    Garnet garnet;
    int textColor = Utils.rgb(255, 255, 255, 255);

    public SceneMenu(String name) {
        super(name);
    }

    @Override
    public void init() {
        String fontFileName = Utils.getPathForResource(this, fontName);
        regularFont = new RegularFont(fontFileName, 12, 12);

        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        resources = SceneManager.getSharedContext().getObjectByType(Resources.class);

        if (resources == null) System.out.println("resources is null");
    }


    @Override
    public void tick(double delta) {
        if (garnet.getInput().isPressed(Input.VirtualButton.FIRE1)) {
            SceneManager.setActiveScene("game");
        }
    }

    @Override
    public void draw() {
        System.out.println("scene draw");
        garnet.setDrawColor(textColor);

        regularFont.clearSpriteBatch();
        regularFont.drawText("Invaders", 20, 100, 3);
        regularFont.render();

        // temp bmf font stuff
        resources.bmfFontTexture.bind();
        garnet.getDisplay().bindTexture(resources.bmfFontTexture.getId());
        resources.bmfFont.drawString(resources.bmfFontTexture, "Garnet Example Project", 20, 20, 2);

        ////////
    }

    @Override
    public void onMakeActive() {

    }

    @Override
    public void onMakeInactive() {

    }
}
