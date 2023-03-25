package com.physmo.garnetexamples.graphics;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnet.Texture;
import com.physmo.garnet.Utils;
import com.physmo.garnet.bitmapfont.BitmapFont;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnettoolkit.color.Color;

import java.io.IOException;

// NOTE: on MacOS we need to add a vm argument: -XstartOnFirstThread
public class BMFontExample extends GarnetApp {

    BitmapFont bmfFont;
    Texture bmfFontTexture;

    public BMFontExample(Garnet garnet, String name) {
        super(garnet, name);
    }

    public static void main(String[] args) {
        Garnet garnet = new Garnet(400, 400);
        GarnetApp app = new BMFontExample(garnet, "");

        garnet.setApp(app);

        garnet.init();
        garnet.run();
    }

    @Override
    public void init(Garnet garnet) {

        String imagePath = Utils.getPathForResource(this, "ptmono16_0.png");
        String definitionPath = Utils.getPathForResource(this, "ptmono16.fnt");

        try {
            bmfFont = new BitmapFont(imagePath, definitionPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void tick(double delta) {
    }

    @Override
    public void draw() {

        Graphics graphics = garnet.getGraphics();

        graphics.setScale(2);
        graphics.setColor(Color.SUNSET_GREEN.toInt());

        bmfFont.drawString(graphics, "hello", 10, 10);

        graphics.render();
    }

}