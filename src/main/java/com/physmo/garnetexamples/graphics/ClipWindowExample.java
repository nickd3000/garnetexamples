package com.physmo.garnetexamples.graphics;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnet.Texture;
import com.physmo.garnet.Utils;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnettoolkit.Vector3;
import com.physmo.garnettoolkit.color.Color;

// NOTE: on MacOS we need to add a vm argument: -XstartOnFirstThread
public class ClipWindowExample extends GarnetApp {

    // TODO: make relative
    private static final String fileName1 = "wood.png";

    Vector3 pos1 = new Vector3();
    Vector3 pos2 = new Vector3();
    Texture texture1;
    double time = 0;

    public ClipWindowExample(Garnet garnet, String name) {
        super(garnet, name);
    }

    public static void main(String[] args) {
        Garnet garnet = new Garnet(400, 400);
        GarnetApp app = new ClipWindowExample(garnet, "");

        garnet.setApp(app);

        garnet.init();
        garnet.run();
    }

    @Override
    public void init(Garnet garnet) {

        texture1 = Texture.loadTexture(Utils.getPathForResource(this, fileName1));

        garnet.getGraphics().addTexture(texture1);

        System.out.println("adding keyboard callback from game container");

        garnet.addKeyboardCallback((key, scancode, action, mods) -> {
            System.out.println("keyboard handler" + scancode + "  " + action);
        });

    }

    @Override
    public void tick(double delta) {
        time += delta;
    }

    @Override
    public void draw() {

        drawTestSpriteBuilder();

        garnet.getGraphics().render();

    }

    private void drawTestSpriteBuilder() {

        Graphics graphics = garnet.getGraphics();

        int offset1 = (int) (Math.sin(time * 3) * 50) - 50;
        int offset2 = (int) (Math.cos(time * 4) * 50) - 50;

        // Draw unscaled sprites using sprite sheet
        graphics.addClipRect(1, 50, 50, 300, 300);
        graphics.setScale(1);
        graphics.setDrawOrder(1);

        graphics.disableClipRect();
        graphics.setColor(Color.WHITE.toInt());
        graphics.drawImage(texture1, offset1, 0);
        graphics.setActiveClipRect(1);
        graphics.setColor(Color.GREEN.toInt());
        graphics.drawImage(texture1, 0, offset2);
        graphics.disableClipRect();

    }

    private void drawNew() {

    }


}
