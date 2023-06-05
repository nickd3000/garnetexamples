package com.physmo.garnetexamples.graphics;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnettoolkit.color.ColorUtils;

// NOTE: on MacOS we need to add a vm argument: -XstartOnFirstThread
public class PrimativeDrawingExample extends GarnetApp {

    public PrimativeDrawingExample(Garnet garnet, String name) {
        super(garnet, name);
    }

    public static void main(String[] args) {
        Garnet garnet = new Garnet(640, 480);
        GarnetApp app = new PrimativeDrawingExample(garnet, "");

        garnet.setApp(app);

        garnet.init();
        garnet.run();
    }

    @Override
    public void init(Garnet garnet) {
    }

    @Override
    public void tick(double delta) {

    }

    @Override
    public void draw(Graphics g) {

        g.setColor(ColorUtils.WHITE);


        g.drawLine(190, 20 + 50, 190 + 100, 20 + 50);

        g.setColor(ColorUtils.SUNSET_ORANGE);
        g.drawRect(20, 20, 100, 100);

        g.setColor(ColorUtils.SUNSET_YELLOW);
        g.filledRect(20, 140, 100, 100);

        g.setColor(ColorUtils.SUNSET_YELLOW);
        g.drawCircle(70, 310, 50, 50);

        g.setColor(ColorUtils.SUNSET_BLUE);
        g.setColor(ColorUtils.asRGBA(1, 0.5f, 1f, 0.4f));
        g.filledCircle(190, 310, 50, 50);
        g.filledCircle(190 + 50, 310, 50, 50);

    }


}
