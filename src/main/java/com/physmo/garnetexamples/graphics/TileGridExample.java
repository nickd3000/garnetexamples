package com.physmo.garnetexamples.graphics;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnet.Texture;
import com.physmo.garnet.Utils;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.spritebatch.TileSheet;
import com.physmo.garnet.tilegrid.TileGridData;
import com.physmo.garnet.tilegrid.TileGridDrawer;

import java.util.Random;

// NOTE: on MacOS we need to add a vm argument: -XstartOnFirstThread
public class TileGridExample extends GarnetApp {

    String imageFileName = "space.png";
    TileSheet tileSheet;
    Texture texture;
    Graphics graphics;
    double x = 0;

    TileGridDrawer tileGridDrawer;
    TileGridData tileGridData;
    Random random = new Random();

    public TileGridExample(Garnet garnet, String name) {
        super(garnet, name);
    }

    public static void main(String[] args) {
        Garnet garnet = new Garnet(400, 400);
        GarnetApp app = new TileGridExample(garnet, "");

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

        int mapWidth = 100;
        int mapHeight = 100;

        tileGridData = new TileGridData(mapWidth, mapHeight);
        tileGridDrawer = new TileGridDrawer()
                .setData(tileGridData)
                .setWindowSize(5, 5)
                .setTileSize(16, 16)
                .setTileSheet(tileSheet);


        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                tileGridData.setTileId(x, y, random.nextInt(5));
            }
        }

        graphics.addClipRect(1, 10, 300, 200, 200);
    }

    @Override
    public void tick(double delta) {
        x += delta * 50;
        if (x > 200) x = 0;

        tileGridDrawer.setScroll(x, x / 2);
    }

    @Override
    public void draw() {
//        graphics.setColor(Color.GREEN.toInt());
//        graphics.setScale(5);
//        graphics.drawImage(tileSheet, (int) x, 5, 2, 2);

        graphics.setActiveClipRect(1);

        tileGridDrawer.draw(graphics, 20, 20);

        graphics.render();
    }

}
