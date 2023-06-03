package com.physmo.garnetexamples.graphics;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnet.Texture;
import com.physmo.garnet.drawablebatch.TileSheet;
import com.physmo.garnet.graphics.Graphics;

// NOTE: on MacOS we need to add a vm argument: -XstartOnFirstThread
public class SimpleSpriteExample extends GarnetApp {

    TileSheet tileSheet;
    Texture texture;
    double x = 0;
    double scale = 4;

    public SimpleSpriteExample(Garnet garnet, String name) {
        super(garnet, name);
    }

    public static void main(String[] args) {
        Garnet garnet = new Garnet(400, 400);
        GarnetApp app = new SimpleSpriteExample(garnet, "");

        garnet.setApp(app);

        garnet.init();
        garnet.run();
    }

    @Override
    public void init(Garnet garnet) {

        texture = Texture.loadTexture("space.png");
        tileSheet = new TileSheet(texture, 16, 16);
        Graphics graphics = garnet.getGraphics();
        graphics.addTexture(texture);
    }

    @Override
    public void tick(double delta) {
        x += delta * 50;
        if (x > 80) x = -16;
    }

    @Override
    public void draw(Graphics g) {
        int[] mousePosition = garnet.getInput().getMousePositionScaled(scale);

        g.setColor(0xaaff22ff);
        g.setScale(scale);
        g.drawImage(tileSheet, (int) x, 5, 2, 2);

        g.setColor(0xaa22ffff);
        g.drawImage(tileSheet, mousePosition[0], mousePosition[1], 2, 2);
        g.render();
    }
}
