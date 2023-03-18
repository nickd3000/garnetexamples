package com.physmo.garnettest.basicsprites;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnet.Texture;
import com.physmo.garnet.Utils;
import com.physmo.garnet.spritebatch.TileSheet;
import com.physmo.garnettoolkit.color.Color;

// NOTE: on MacOS we need to add a vm argument: -XstartOnFirstThread
public class SimpleSpriteExample extends GarnetApp {

    String imageFileName = "space.png";
    TileSheet tileSheet;
    Texture texture;
    double x = 0;

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
        texture = Texture.loadTexture(Utils.getPathForResource(this, imageFileName));
        tileSheet = new TileSheet(texture, 16, 16);
        garnet.getGraphics().addTexture(texture);
    }

    @Override
    public void tick(double delta) {
        x += delta * 50;
        if (x > 80) x = -16;
    }

    @Override
    public void draw() {
        garnet.getGraphics().setColor(Color.GREEN.toInt());
        garnet.getGraphics().setScale(5);
        garnet.getGraphics().drawImage(tileSheet, (int) x, 5, 2, 2);
        garnet.getGraphics().render();
    }

}
