package com.physmo.garnetexamples.graphics;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnet.regularfont.RegularFont;
import com.physmo.garnettoolkit.color.Color;

// NOTE: on MacOS we need to add a vm argument: -XstartOnFirstThread
public class RegularFontExample extends GarnetApp {

    RegularFont regularFont;

    public RegularFontExample(Garnet garnet, String name) {
        super(garnet, name);
    }

    public static void main(String[] args) {
        Garnet garnet = new Garnet(400, 400);
        GarnetApp app = new RegularFontExample(garnet, "");

        garnet.setApp(app);

        garnet.init();
        garnet.run();
    }

    @Override
    public void init(Garnet garnet) {
        regularFont = new RegularFont("regularfonts/12x12Font.png", 12, 12);
        garnet.getGraphics().setBackgroundColor(Color.SUNSET_BLUE.toInt());
    }

    @Override
    public void tick(double delta) {
    }

    @Override
    public void draw() {

        garnet.getGraphics().setScale(2);
        garnet.getGraphics().setColor(Color.SUNSET_GREEN.toInt());
        regularFont.drawText(garnet.getGraphics(), "Regular font", 0, 10);

        garnet.getGraphics().setScale(3);
        garnet.getGraphics().setColor(Color.SUNSET_YELLOW.toInt());
        regularFont.drawText(garnet.getGraphics(), "example", 0, 26);

        garnet.getGraphics().setScale(1);
        garnet.getGraphics().setColor(Color.SUNSET_GREEN.toInt());
        regularFont.setHorizontalPad(2);
        regularFont.drawText(garnet.getGraphics(), "horizontal pad 2", 0, 140);

        garnet.getGraphics().setScale(1);
        garnet.getGraphics().setColor(Color.SUNSET_YELLOW.toInt());
        regularFont.setHorizontalPad(-3);
        regularFont.drawText(garnet.getGraphics(), "horizontal pad -3 minimum", 0, 180);

        regularFont.setHorizontalPad(0);


        garnet.getGraphics().render();
    }

}
