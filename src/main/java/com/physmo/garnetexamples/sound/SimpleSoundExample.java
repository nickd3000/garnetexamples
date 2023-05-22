package com.physmo.garnetexamples.sound;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnet.Texture;
import com.physmo.garnet.drawablebatch.TileSheet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnettoolkit.color.Color;

// NOTE: on MacOS we need to add a vm argument: -XstartOnFirstThread
public class SimpleSoundExample extends GarnetApp {

    String imageFileName = "space.png";
    String soundFileName = "space.png";
    TileSheet tileSheet;
    Texture texture;
    Graphics graphics;
    double x = 0;
    double scale = 4;

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

        texture = Texture.loadTexture(imageFileName);
        tileSheet = new TileSheet(texture, 16, 16);
        graphics = garnet.getGraphics();
        graphics.addTexture(texture);

        int soundA = garnet.getSound().loadSound("sounds/synth.wav");
        int soundB = garnet.getSound().loadSound("sounds/laserShoot-3.wav");

        garnet.getSound().playSound(soundA);
        garnet.getSound().playSound(soundB);
    }

    @Override
    public void tick(double delta) {
        x += delta * 50;
        if (x > 80) x = -16;
    }

    @Override
    public void draw() {
        int[] mousePosition = garnet.getInput().getMousePositionScaled(scale);

        graphics.setColor(Color.GREEN.toInt());
        graphics.setScale(scale);
        graphics.drawImage(tileSheet, (int) x, 5, 2, 2);

        graphics.setColor(Color.SUNSET_BLUE.toInt());
        graphics.drawImage(tileSheet, mousePosition[0], mousePosition[1], 2, 2);
        graphics.render();

    }

}
