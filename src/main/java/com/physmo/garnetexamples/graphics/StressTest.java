package com.physmo.garnetexamples.graphics;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnet.Texture;
import com.physmo.garnet.Utils;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.spritebatch.TileSheet;
import com.physmo.garnetexamples.graphics.support.FloatingInvaderComponent;
import com.physmo.garnettoolkit.Context;
import com.physmo.garnettoolkit.GameObject;
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

    int numPoints = 5000;
    List<Vector3> points = new ArrayList<>();
    List<Vector3> dirs = new ArrayList<>();

    public StressTest(Garnet garnet, String name) {
        super(garnet, name);
    }

    Context context;

    public static void main(String[] args) {
        Garnet garnet = new Garnet(640, 480);
        GarnetApp app = new StressTest(garnet, "");

        garnet.setApp(app);

        garnet.init();
        garnet.run();
    }

    @Override
    public void init(Garnet garnet) {
        context = new Context();

        texture = Texture.loadTexture(Utils.getPathForResource(this, imageFileName));
        tileSheet = new TileSheet(texture, 16, 16);
        graphics = garnet.getGraphics();
        graphics.addTexture(texture);

        context.add(tileSheet);
        context.add(graphics);

        for (int i = 0; i < 50000; i++) {
            GameObject gameObject = new GameObject("");
            gameObject.addComponent(new FloatingInvaderComponent(garnet.getDisplay().getWindowWidth(), garnet.getDisplay().getWindowHeight()));
            context.add(gameObject);
        }
    }

    @Override
    public void tick(double delta) {
        context.tick(delta);
    }

    @Override
    public void draw() {
        graphics.setColor(Color.GREEN.toInt());
        graphics.setScale(1);

        context.draw();

        graphics.render();
    }

}
