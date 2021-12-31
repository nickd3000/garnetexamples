package com.physmo.garnettest.tilegridtest;

import com.physmo.garnet.GameState;
import com.physmo.garnet.Garnet;
import com.physmo.garnet.Texture;
import com.physmo.garnet.spritebatch.Sprite2D;
import com.physmo.garnet.spritebatch.SpriteBatch;
import com.physmo.garnet.tilegrid.TileGridData;
import com.physmo.garnet.tilegrid.TileGridDrawer;

// NOTE: on MacOS we need to add a vm argument: -XstartOnFirstThread
public class ContainerTileGridTest extends GameState {

    private static String fileName = "/Users/nick/Dev/java/garnettest/src/main/resources/space.PNG";
    private static float angle = 0;
    double movementX;
    TileGridData tileGridData;
    TileGridDrawer tileGridDrawer = new TileGridDrawer();
    private Texture texture;
    private SpriteBatch spriteBatch;

    public ContainerTileGridTest(Garnet garnet, String name) {
        super(garnet, name);
    }

    public static void main(String[] args) {
        Garnet garnet = new Garnet(640, 480);
        garnet.addState( new ContainerTileGridTest(garnet, "ContainerTileGridTest"));
        garnet.init();
        garnet.run();
    }

    @Override
    public void init(Garnet garnet) {

        int gridWidth = 100;
        int gridHeight = 100;
        tileGridData = new TileGridData(gridWidth, gridHeight);
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                tileGridData.setTileId(x, y, (int) (Math.random() * 5));
            }
        }

        texture = Texture.loadTexture(fileName);
        spriteBatch = new SpriteBatch(texture);

        tileGridDrawer
                .setData(tileGridData)
                .setSpriteBatch(spriteBatch)
                .setWindowSize(40, 30);

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

        tileGridDrawer.setScroll(movementX * 160, movementX * 60);
    }

    @Override
    public void draw() {


        //drawTestSpriteBuilder();

        tileGridDrawer.setScroll(movementX * 160, movementX * 60);
        tileGridDrawer.draw();
        tileGridDrawer.setScroll(movementX * 130, movementX * 230);
        tileGridDrawer.draw();
        tileGridDrawer.setScroll(movementX * 200, movementX * 90);
        tileGridDrawer.draw();
        tileGridDrawer.setScroll(movementX * 80, movementX * 160);
        tileGridDrawer.draw();
        tileGridDrawer.setScroll(movementX * 30, movementX * 30);
        tileGridDrawer.draw();
        tileGridDrawer.setScroll(movementX * 200, movementX * 200);
        tileGridDrawer.draw();

        spriteBatch.render(1);
        spriteBatch.clear();

    }

    private void drawTestSpriteBuilder() {
        int space = 30;
        int x = 20;

        // Moving
        spriteBatch.add(Sprite2D.build(20 + (int) (movementX * 300), 20 + 40, 16, 16, 0, 0, 16, 16));

        // Unscaled
        spriteBatch.add(Sprite2D.build(x, 20, 16, 16, 0, 0, 16, 16));
        x += space;

        // Coloured
        spriteBatch.add(Sprite2D.build(x, 20, 16, 16, 0, 0, 16, 16)
                .addColor(1.0f, 0.5f, 0.25f));
        x += space;

        // Rotated
        spriteBatch.add(Sprite2D.build(x, 20, 16, 16, 0, 0, 16, 16)
                .addAngle(angle));
        x += space;

        // Scaled
        spriteBatch.add(Sprite2D.build(x, 20, 16 * 2, 16 * 2, 0, 0, 16, 16));
        x += space * 2;

        // Tile setter
        spriteBatch.add(Sprite2D.build(x, 20, 16 * 5, 16 * 5)
                .setTile(6, 0, 16)
                .addColor(0.25f, 0.5f, 1.0f));

    }


}
