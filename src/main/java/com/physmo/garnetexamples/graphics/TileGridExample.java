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

    String imageFileName = "prototypeArt.png";
    TileSheet tileSheet;
    Texture texture;
    Graphics graphics;
    double x = 0;

    TileGridDrawer tileGridDrawer;
    TileGridData tileGridData;
    Random random = new Random();

    int wallTile;
    int grassTile;

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

        wallTile = tileSheet.getTileIndexFromCoords(0, 7);
        grassTile = tileSheet.getTileIndexFromCoords(1, 7);

        int mapWidth = 11;
        int mapHeight = 11;

        tileGridData = new TileGridData(mapWidth, mapHeight);
        tileGridDrawer = new TileGridDrawer()
                .setData(tileGridData)
                .setWindowSize(300, 300)
                .setTileSize(16, 16)
                .setTileSheet(tileSheet)
                .setScale(3);


        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                int tileId = grassTile;//random.nextInt(5);
                if (x == 0 || y == 0 || x == mapWidth - 1 || y == mapHeight - 1) tileId = wallTile;
                tileGridData.setTileId(x, y, tileId);
            }
        }


    }

    @Override
    public void tick(double delta) {
        x += delta * 30;


        tileGridDrawer.setScroll(x, x / 2);
    }

    @Override
    public void draw() {

        tileGridDrawer.draw(graphics, 50, 50);

        graphics.render();
    }

}
