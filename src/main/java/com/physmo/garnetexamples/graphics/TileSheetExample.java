package com.physmo.garnetexamples.graphics;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.graphics.Texture;
import com.physmo.garnet.graphics.TileSheet;

// NOTE: on MacOS we need to add a vm argument: -XstartOnFirstThread
public class TileSheetExample extends GarnetApp {

    TileSheet tileSheet;
    Texture texture;
    double xPos = 0;
    double scale = 4;

    int RED = 0xff5555ff;
    int GREEN = 0x55ff55ff;
    int BLUE = 0x5555ffff;

    public TileSheetExample(Garnet garnet, String name) {
        super(garnet, name);
    }

    public static void main(String[] args) {
        Garnet garnet = new Garnet(400, 400);
        GarnetApp app = new TileSheetExample(garnet, "");

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

        garnet.getDebugDrawer().setColor(0xff00ffff);
        garnet.getDebugDrawer().setVisible(true);
        garnet.getDebugDrawer().setDrawMouseCoords(true);
    }

    @Override
    public void tick(double delta) {
        xPos += delta * 50;
        if (xPos > 80) xPos = -16;
    }

    @Override
    public void draw(Graphics g) {
        g.setScale(scale);

        // Retrieve the current mouse position.
        int[] mousePosition = garnet.getInput().getMousePositionScaled(scale);


        g.setColor(RED);
        g.drawImage(tileSheet, (int) xPos, 5, 2, 2);

        g.setColor(GREEN);
        g.drawImage(tileSheet.getSubImage(2, 2), (int) xPos + 20, 5);

        g.setColor(BLUE);
        g.drawImage(tileSheet, mousePosition[0], mousePosition[1], 2, 2);
    }
}

