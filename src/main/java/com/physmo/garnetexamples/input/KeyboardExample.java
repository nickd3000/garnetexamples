package com.physmo.garnetexamples.input;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnet.Texture;
import com.physmo.garnet.Utils;
import com.physmo.garnet.drawablebatch.TileSheet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.input.Input;

// NOTE: on MacOS we need to add a vm argument: -XstartOnFirstThread
public class KeyboardExample extends GarnetApp {

    String imageFileName = "space.png";
    TileSheet tileSheet;
    Texture texture;
    Graphics graphics;

    boolean up, down, left, right;

    public KeyboardExample(Garnet garnet, String name) {
        super(garnet, name);
    }

    public static void main(String[] args) {
        Garnet garnet = new Garnet(400, 400);
        GarnetApp app = new KeyboardExample(garnet, "");

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
        right = garnet.getInput().isPressed(Input.VirtualButton.RIGHT);
        left = garnet.getInput().isPressed(Input.VirtualButton.LEFT);
        up = garnet.getInput().isPressed(Input.VirtualButton.UP);
        down = garnet.getInput().isPressed(Input.VirtualButton.DOWN);
    }

    @Override
    public void draw() {
        garnet.getDebugDrawer().setScale(2);

        garnet.getDebugDrawer().setUserString("UP:    ", Boolean.toString(up));
        garnet.getDebugDrawer().setUserString("DOWN:  ", Boolean.toString(down));
        garnet.getDebugDrawer().setUserString("LEFT:  ", Boolean.toString(left));
        garnet.getDebugDrawer().setUserString("RIGHT: ", Boolean.toString(right));

        graphics.render();
    }

}
