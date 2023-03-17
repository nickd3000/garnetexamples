package com.physmo.garnettest.basicsprites;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnet.Texture;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.spritebatch.TileSheet;
import com.physmo.garnettoolkit.color.Color;

// NOTE: on MacOS we need to add a vm argument: -XstartOnFirstThread
public class ContainerBasicSprites extends GarnetApp {

    // TODO: make relative
    private static String fileName1 = "/Users/nick/Dev/java/garnettest/src/main/resources/space.PNG";
    private static String fileName2 = "/Users/nick/Dev/java/garnettest/src/main/resources/prototypeArt.PNG";

    private static float angle = 0;
    double movementX;
    private static int FOREGROUND_LAYER = 2;
    private static int BACKGROUND_LAYER = 1;
    TileSheet tileSheet1;
    TileSheet tileSheet2;
    private Texture texture1;
    private Texture texture2;

    public ContainerBasicSprites(Garnet garnet, String name) {
        super(garnet, name);
    }

    public static void main(String[] args) {
        Garnet garnet = new Garnet(640, 480);
        GarnetApp app = new ContainerBasicSprites(garnet, "");

        garnet.setApp(app);

        garnet.init();
        garnet.run();
    }

    @Override
    public void init(Garnet garnet) {

        texture1 = Texture.loadTexture(fileName1);
        tileSheet1 = new TileSheet(texture1, 16, 16);
        garnet.getGraphics().addTexture(texture1);

        texture2 = Texture.loadTexture(fileName2);
        tileSheet2 = new TileSheet(texture2, 16, 16);
        garnet.getGraphics().addTexture(texture2);

        System.out.println("adding keyboard callback from game container");

        garnet.addKeyboardCallback((key, scancode, action, mods) -> {
            System.out.println("keyboard handler" + scancode + "  " + action);
        });

    }

    @Override
    public void tick(double delta) {
        angle += delta * 100f;
        movementX += delta * 0.2;
        while (movementX > 1.0f) {
            movementX -= 1.0f;
        }
    }

    @Override
    public void draw() {

        drawTestSpriteBuilder();

        garnet.getGraphics().render();

    }

    private void drawTestSpriteBuilder() {
        int space = 30;
        int x = 20;

        Graphics graphics = garnet.getGraphics();

        graphics.setColor(Color.WHITE.toInt());

        // Draw unscaled sprites using sprite sheet
        graphics.setScale(1);
        graphics.setDrawOrder(1);
        graphics.drawImage(tileSheet1, 0, 10, 0, 0);
        graphics.drawImage(tileSheet1, 64, 10, 1, 0);
        graphics.drawImage(tileSheet2, 128, 10, 1, 0);

        // Draw scaled sprites using sprite sheet
        graphics.setScale(3);
        graphics.drawImage(tileSheet1, 0, 30, 0, 0);
        graphics.drawImage(tileSheet1, 64, 30, 1, 0);
        graphics.drawImage(tileSheet2, 128, 30, 1, 0);

        // Draw scaled and tinted sprites using sprite sheet
        graphics.setScale(3);
        graphics.setColor(Color.RED.toInt());
        graphics.drawImage(tileSheet1, 0, 60, 1, 0);
        graphics.setColor(Color.GREEN.toInt());
        graphics.drawImage(tileSheet1, 64, 60, 1, 0);
        graphics.setColor(Color.YELLOW.toInt());
        graphics.drawImage(tileSheet1, 128, 60, 1, 0);

        // Using draw order
        graphics.setScale(4);
        graphics.setColor(Color.WHITE.toInt());
        graphics.setDrawOrder(BACKGROUND_LAYER);
        graphics.drawImage(tileSheet2, 0, 70, 5, 2);
        graphics.setDrawOrder(FOREGROUND_LAYER);
        graphics.drawImage(tileSheet2, 0 + 4, 70 + 4, 0, 3);

        graphics.setDrawOrder(FOREGROUND_LAYER);
        graphics.drawImage(tileSheet2, 30, 70, 5, 2);
        graphics.setDrawOrder(BACKGROUND_LAYER);
        graphics.drawImage(tileSheet2, 30 + 4, 70 + 4, 0, 3);


        // Moving
        //spriteBatch.add(Sprite2D.build(20 + (int) (movementX * 300), 20 + 40, 16, 16, 0, 0, 16, 16));

        // Unscaled
        //spriteBatch.add(Sprite2D.build(x, 20, 16, 16, 0, 0, 16, 16));
        x += space;

        // Coloured
        //spriteBatch.add(Sprite2D.build(x, 20, 16, 16, 0, 0, 16, 16).addColor(1.0f, 0.5f, 0.25f));
        x += space;

        // Rotated
        //spriteBatch.add(Sprite2D.build(x, 20, 16, 16, 0, 0, 16, 16).addAngle(angle));
        x += space;

        // Scaled
        //spriteBatch.add(Sprite2D.build(x, 20, 16 * 2, 16 * 2, 0, 0, 16, 16));
        x += space * 2;

        // Tile setter
        //spriteBatch.add(Sprite2D.build(x, 20, 16 * 5, 16 * 5).setTile(6, 0, 16).addColor(0.25f, 0.5f, 1.0f));

    }

    private void drawNew() {

    }


}
