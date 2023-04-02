package com.physmo.garnetexamples.input;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnet.Texture;
import com.physmo.garnet.Utils;
import com.physmo.garnet.drawablebatch.TileSheet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnettoolkit.color.Color;

// NOTE: on MacOS we need to add a vm argument: -XstartOnFirstThread
public class MouseExample extends GarnetApp {

    String imageFileName = "space.png";
    TileSheet tileSheet;
    Texture texture;
    Graphics graphics;

    double scale = 3;

    public MouseExample(Garnet garnet, String name) {
        super(garnet, name);
    }

    public static void main(String[] args) {
        Garnet garnet = new Garnet(400, 400);
        GarnetApp app = new MouseExample(garnet, "");

        garnet.setApp(app);

        garnet.init();
        garnet.run();
    }

    @Override
    public void init(Garnet garnet) {
        texture = Texture.loadTexture(Utils.getPathForResource(this, imageFileName));
        tileSheet = new TileSheet(texture, 16, 16);
        graphics = garnet.getGraphics();
        graphics.addTexture(texture);
    }

    @Override
    public void tick(double delta) {

    }

    @Override
    public void draw() {

        int[] mp, mps;

        mps = garnet.getInput().getMousePositionScaled(scale);
        mp = garnet.getInput().getMousePosition();
        garnet.getDebugDrawer().setUserString("Mouse pos:       ", mp[0] + "," + mp[1]);
        garnet.getDebugDrawer().setUserString("Mouse pos scaled:", mps[0] + "," + mps[1]);

        graphics.setScale(scale);
        graphics.setColor(Color.SUNSET_BLUE.toInt());
        graphics.drawImage(tileSheet, mps[0] - 8, mps[1] - 8, 2, 2);

        graphics.render();

    }

}
