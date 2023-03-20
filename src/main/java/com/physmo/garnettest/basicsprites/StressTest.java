package com.physmo.garnettest.basicsprites;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnet.Texture;
import com.physmo.garnet.Utils;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.spritebatch.TileSheet;
import com.physmo.garnettoolkit.Vector3;
import com.physmo.garnettoolkit.color.Color;

import java.util.ArrayList;
import java.util.List;

// NOTE: on MacOS we need to add a vm argument: -XstartOnFirstThread
public class StressTest extends GarnetApp {

    String imageFileName = "space.png";
    TileSheet tileSheet;
    Texture texture;
    Graphics graphics;
    double x = 0;

    int numPoints = 10000;
    List<Vector3> points = new ArrayList<>();
    List<Vector3> dirs = new ArrayList<>();

    public StressTest(Garnet garnet, String name) {
        super(garnet, name);
    }

    public static void main(String[] args) {
        Garnet garnet = new Garnet(640, 480);
        GarnetApp app = new StressTest(garnet, "");

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
        int maxPoints = 100000;
        for (int i = 0; i < maxPoints; i++) {
            points.add(new Vector3(Math.random() * 100, Math.random() * 100, 0));
            dirs.add(new Vector3(Math.random() - 0.5, Math.random() - 0.5, 0));
        }
    }

    @Override
    public void tick(double delta) {
        double width = 640;
        double height = 480;

        for (int i = 0; i < numPoints; i++) {
            Vector3 p = points.get(i);
            p.addi(dirs.get(i));
            if (p.x > width) p.x -= width;
            if (p.x < 0) p.x += width;
            if (p.y > height) p.y -= height;
            if (p.y < 0) p.y += height;
        }
    }

    @Override
    public void draw() {
        graphics.setColor(Color.GREEN.toInt());
        graphics.setScale(1);
        for (int i = 0; i < numPoints; i++) {
            Vector3 p = points.get(i);
            graphics.drawImage(tileSheet, (int) p.x, (int) p.y, 2, 2);
        }
        graphics.render();
    }

}
