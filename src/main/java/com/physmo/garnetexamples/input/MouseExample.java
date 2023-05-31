package com.physmo.garnetexamples.input;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnet.Texture;
import com.physmo.garnet.Utils;
import com.physmo.garnet.drawablebatch.TileSheet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.input.Input;
import com.physmo.garnettoolkit.color.Color;

import java.io.InputStream;

// NOTE: on MacOS we need to add a vm argument: -XstartOnFirstThread
public class MouseExample extends GarnetApp {

    String imageFileName = "space.png";
    TileSheet tileSheet;
    Texture texture;
    Graphics graphics;
    Input input;
    boolean mousePressed = false;
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
        InputStream inputStream = Utils.getFileFromResourceAsStream(imageFileName);
        texture = Texture.loadTexture(inputStream);
        tileSheet = new TileSheet(texture, 16, 16);
        graphics = garnet.getGraphics();
        graphics.addTexture(texture);
        input = garnet.getInput();
        garnet.getDebugDrawer().setVisible(true);
    }

    @Override
    public void tick(double delta) {
        mousePressed = input.isMouseButtonPressed(Input.MOUSE_BUTTON_LEFT);
    }

    @Override
    public void draw() {

        int[] mp, mps;

        mps = input.getMousePositionScaled(scale);
        mp = input.getMousePosition();

        garnet.getDebugDrawer().setUserString("Mouse pos:        ", String.format("%d,%d", mp[0], mp[1]));
        garnet.getDebugDrawer().setUserString("Mouse pos scaled: ", String.format("%d,%d", mps[0], mps[1]));

        graphics.setScale(scale);

        if (mousePressed) graphics.setColor(Color.SUNSET_RED.toInt());
        else graphics.setColor(Color.SUNSET_YELLOW.toInt());

        graphics.drawImage(tileSheet, mps[0] - 8, mps[1] - 8, 2, 2);

        graphics.render();

    }

}
