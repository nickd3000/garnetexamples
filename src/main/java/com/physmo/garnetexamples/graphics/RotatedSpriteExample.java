package com.physmo.garnetexamples.graphics;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnet.Texture;
import com.physmo.garnet.drawablebatch.TileSheet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnettoolkit.color.Color;

// NOTE: on MacOS we need to add a vm argument: -XstartOnFirstThread
public class RotatedSpriteExample extends GarnetApp {

    String imageFileName = "space.png";
    TileSheet tileSheet;
    Texture texture;
    double x = 0;
    double scale = 4;
    double angle = 0;

    public RotatedSpriteExample(Garnet garnet, String name) {
        super(garnet, name);
    }

    public static void main(String[] args) {
        Garnet garnet = new Garnet(400, 400);
        GarnetApp app = new RotatedSpriteExample(garnet, "");

        garnet.setApp(app);

        garnet.init();
        garnet.run();
    }

    @Override
    public void init(Garnet garnet) {

        texture = Texture.loadTexture(imageFileName);
        tileSheet = new TileSheet(texture, 16, 16);
        Graphics graphics = garnet.getGraphics();
        graphics.addTexture(texture);
        garnet.getDebugDrawer().setVisible(true);
    }

    @Override
    public void tick(double delta) {
        x += delta * 30;
        if (x > 80) x = -16;
        angle += delta * 30;

        garnet.getDebugDrawer().setUserString("LPS", Double.toString(garnet.getGameClock().getLps()));
        garnet.getDebugDrawer().setUserString("x    ", String.valueOf(x));
        garnet.getDebugDrawer().setUserString("angle", String.valueOf(angle));
    }

    @Override
    public void draw(Graphics g) {
        int[] mousePosition = garnet.getInput().getMousePositionScaled(scale);

        g.setColor(Color.GREEN.toInt());
        g.setScale(scale);
        g.drawImage(tileSheet, (int) x, 5, 2, 2);

        g.setColor(Color.GREEN.toInt());
        g.setScale(scale);
        g.drawImage(tileSheet, (int) x, 20, 2, 2, angle);

        g.setColor(Color.SUNSET_BLUE.toInt());
        g.drawImage(tileSheet, mousePosition[0], mousePosition[1], 2, 2, -angle / 2);

    }

}
