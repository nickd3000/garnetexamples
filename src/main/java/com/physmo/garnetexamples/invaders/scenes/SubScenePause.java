package com.physmo.garnetexamples.invaders.scenes;


import com.physmo.garnet.Garnet;
import com.physmo.garnet.Utils;
import com.physmo.garnet.input.Input;
import com.physmo.garnet.regularfont.RegularFont;
import com.physmo.garnettoolkit.Scene;
import com.physmo.garnettoolkit.SceneManager;

public class SubScenePause extends Scene {

    public static final String fontName = "/12x12Font.png";
    RegularFont regularFont;
    Garnet garnet;

    public SubScenePause(String name) {
        super(name);
    }

    @Override
    public void init() {
        System.out.println("init state pause");

        String fontFileName = Utils.getPathForResource(this, fontName);
        regularFont = new RegularFont(fontFileName, 12, 12);

        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
    }

    @Override
    public void tick(double delta) {

        if (garnet.getInput().isFirstPress(Input.VirtualButton.MENU)) {
            SceneManager.popSubScene("pause");
        }

    }

    @Override
    public void draw() {
//        regularFont.clearSpriteBatch();
//        regularFont.drawText("PAUSED", 20, 100, 3);
//        regularFont.render();
    }

    @Override
    public void onMakeActive() {

    }

    @Override
    public void onMakeInactive() {

    }
}
