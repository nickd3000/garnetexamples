package com.physmo.garnetexamples.sound;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnet.Utils;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.input.Input;
import com.physmo.garnet.text.RegularFont;
import com.physmo.garnettoolkit.color.ColorUtils;

// NOTE: on MacOS we need to add a vm argument: -XstartOnFirstThread
public class SimpleSoundExample extends GarnetApp {

    RegularFont regularFont;
    int soundA;
    int soundB;

    public SimpleSoundExample(Garnet garnet, String name) {
        super(garnet, name);
    }

    public static void main(String[] args) {
        Garnet garnet = new Garnet(400, 400);
        GarnetApp app = new SimpleSoundExample(garnet, "");

        garnet.setApp(app);

        garnet.init();
        garnet.run();
    }


    @Override
    public void init(Garnet garnet) {
        regularFont = new RegularFont("regularfonts/12x12Font.png", 12, 12);

        soundA = garnet.getSound().loadSound(Utils.getPathForResource(this, "sounds/laserShoot-3.wav"));
        soundB = garnet.getSound().loadSound(Utils.getPathForResource(this, "sounds/laserShoot.wav"));

        garnet.getSound().playSound(soundA);
        garnet.getSound().playSound(soundB);
    }

    @Override
    public void tick(double delta) {

        if (garnet.getInput().isMouseButtonFirstPress(Input.MOUSE_BUTTON_LEFT)) {
            garnet.getSound().playSound(soundA);
        }
        if (garnet.getInput().isMouseButtonFirstPress(Input.MOUSE_BUTTON_RIGHT)) {
            garnet.getSound().playSound(soundB);
        }
    }

    @Override
    public void draw(Graphics g) {

        g.setColor(ColorUtils.GREEN);
        g.setZoom(2);

        g.setColor(ColorUtils.SUNSET_BLUE);

        regularFont.setHorizontalPad(-4);

        regularFont.drawText(g, "Left / Right mouse", 20, 20);
        regularFont.drawText(g, "button to play", 20, 35);
        regularFont.drawText(g, "sounds", 20, 50);

    }

    public float clamp(float v) {
        if (v < 0) return 0;
        if (v > 1) return 1;
        return v;
    }

}
