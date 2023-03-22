package com.physmo.garnetexamples.misc;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnet.Texture;
import com.physmo.garnet.Utils;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.spritebatch.TileSheet;
import com.physmo.garnettoolkit.color.Color;
import com.physmo.garnettoolkit.curve.CurveType;
import com.physmo.garnettoolkit.curve.StandardCurve;

// NOTE: on MacOS we need to add a vm argument: -XstartOnFirstThread
public class CurveMotionExample extends GarnetApp {

    String imageFileName = "space.png";
    TileSheet tileSheet;
    Texture texture;
    Graphics graphics;
    double x = 0;
    int dir = 1;
    double pos = 0;
    StandardCurve standardCurve_EASE_IN_SINE;

    StandardCurve standardCurve_LINE_DOWN;
    StandardCurve standardCurve_LINE_UP;
    StandardCurve standardCurve_LINE_FLAT;
    StandardCurve standardCurve_EASE_OUT_SINE;

    public CurveMotionExample(Garnet garnet, String name) {
        super(garnet, name);
    }

    public static void main(String[] args) {
        Garnet garnet = new Garnet(400, 400);
        GarnetApp app = new CurveMotionExample(garnet, "");

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

        standardCurve_EASE_IN_SINE = new StandardCurve(CurveType.EASE_IN_SINE);
        standardCurve_LINE_DOWN = new StandardCurve(CurveType.LINE_DOWN);
        standardCurve_LINE_UP = new StandardCurve(CurveType.LINE_UP);
        standardCurve_LINE_FLAT = new StandardCurve(CurveType.LINE_FLAT);
        standardCurve_EASE_OUT_SINE = new StandardCurve(CurveType.EASE_OUT_SINE);
    }

    @Override
    public void tick(double delta) {
        pos += delta * 0.5 * (double) dir;
        if (pos > 1) {
            pos = 1;
            dir = -1;
        } else if (pos < 0) {
            pos = 0;
            dir = 1;
        }
    }

    @Override
    public void draw() {
        graphics.setColor(Color.GREEN.toInt());
        graphics.setScale(2);
        double width = 200 - 32;
        int y = 2;

        x = standardCurve_EASE_IN_SINE.value(pos);
        graphics.drawImage(tileSheet, (int) (x * width), (y++) * 16, 2, 2);
        x = standardCurve_LINE_DOWN.value(pos);
        graphics.drawImage(tileSheet, (int) (x * width), (y++) * 16, 2, 2);
        x = StandardCurve.LINE_UP.value(pos);
        graphics.drawImage(tileSheet, (int) (x * width), (y++) * 16, 2, 2);
        x = standardCurve_LINE_FLAT.value(pos);
        graphics.drawImage(tileSheet, (int) (x * width), (y++) * 16, 2, 2);
        x = standardCurve_EASE_OUT_SINE.value(pos);
        graphics.drawImage(tileSheet, (int) (x * width), (y++) * 16, 2, 2);


        graphics.render();
    }

}
