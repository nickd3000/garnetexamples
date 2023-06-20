package com.physmo.garnetexamples.graphics;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnet.Utils;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.input.Input;
import com.physmo.garnettoolkit.color.ColorUtils;

// NOTE: on MacOS we need to add a vm argument: -XstartOnFirstThread
public class PrimitiveDrawingExample extends GarnetApp {

    public PrimitiveDrawingExample(Garnet garnet, String name) {
        super(garnet, name);
    }

    public static void main(String[] args) {
        Garnet garnet = new Garnet(640, 480);
        GarnetApp app = new PrimitiveDrawingExample(garnet, "");

        garnet.setApp(app);

        garnet.init();
        garnet.run();
    }

    @Override
    public void init(Garnet garnet) {
        garnet.getDisplay().setWindowTitle("Primitive drawing example");
    }

    @Override
    public void tick(double delta) {

    }

    @Override
    public void draw(Graphics g) {

        g.setColor(ColorUtils.WHITE);

        double[] mpn = garnet.getInput().getMousePositionNormalised();
        if (garnet.getInput().isMouseButtonPressed(Input.MOUSE_BUTTON_LEFT)) {
            g.setZoom(1 + (mpn[0] * 0.5));
        } else {
            g.setZoom(1);
        }

        g.drawLine(190, 20 + 50, 190 + 100, 20 + 50);

        g.setColor(Utils.rgb(255, 0, 0, 255));
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
