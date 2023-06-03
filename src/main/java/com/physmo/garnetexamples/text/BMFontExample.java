package com.physmo.garnetexamples.text;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnet.bitmapfont.BitmapFont;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnettoolkit.color.Color;

import java.io.IOException;

// NOTE: on MacOS we need to add a vm argument: -XstartOnFirstThread
public class BMFontExample extends GarnetApp {

    BitmapFont bmfFont;

    public BMFontExample(Garnet garnet, String name) {
        super(garnet, name);
    }

    public static void main(String[] args) {
        Garnet garnet = new Garnet(400, 300);
        GarnetApp app = new BMFontExample(garnet, "");

        garnet.setApp(app);

        garnet.init();
        garnet.run();
    }

    @Override
    public void init(Garnet garnet) {

        String imagePath = "bitmapfonts/ptmono16_0.png";
        String definitionPath = "bitmapfonts/ptmono16.fnt";

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
    public void draw(Graphics g) {

        g.setScale(2);
        g.setColor(Color.SUNSET_GREEN.toInt());
        bmfFont.drawText(g, "hello", 0, 10);

        g.setScale(3);
        g.setColor(Color.SUNSET_YELLOW.toInt());
        bmfFont.drawText(g, "hello but bigger", 0, 20);

    }

}
