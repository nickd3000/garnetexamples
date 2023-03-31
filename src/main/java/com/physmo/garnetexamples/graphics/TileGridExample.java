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
    double scrollX = 0;
    double scrollY = 0;
    int scrollDir = 3;
    double scrollTimer = 0;
    int scale = 2;

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

        int mapWidth = 15;
        int mapHeight = 15;

        tileGridData = new TileGridData(mapWidth, mapHeight);
        tileGridDrawer = new TileGridDrawer()
                .setData(tileGridData)
                .setWindowSize(100, 100)
                .setTileSize(16, 16)
                .setTileSheet(tileSheet)
                .setScale(scale);


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
        scrollTimer -= delta;
        double scrollSpeed = 45;
        if (scrollDir == 0) scrollX += delta * scrollSpeed;
        if (scrollDir == 1) scrollX -= delta * scrollSpeed;
        if (scrollDir == 2) scrollY += delta * scrollSpeed;
        if (scrollDir == 3) scrollY -= delta * scrollSpeed;
        if (scrollTimer <= 0) {
            scrollDir = random.nextInt(4);
            scrollTimer = 3 + random.nextInt(3);
        }

        int[] scrollExtents = tileGridDrawer.getScrollExtents();
        if (scrollX <= 0 && scrollDir == 1) scrollTimer = 0;
        if (scrollY <= 0 && scrollDir == 3) scrollTimer = 0;
        if (scrollX >= scrollExtents[0] && scrollDir == 0) scrollTimer = 0;
        if (scrollY >= scrollExtents[1] && scrollDir == 2) scrollTimer = 0;

        //System.out.println("extents "+scrollExtents[0]+", "+scrollExtents[1]);

        tileGridDrawer.setScroll(scrollX, scrollY);
    }

    @Override
    public void draw() {

        tileGridDrawer.draw(graphics, 16, 16);

        int[] pos = tileGridDrawer.translateMapToScreenPosition(16, 16);
        graphics.setActiveClipRect(tileGridDrawer.getClipRectId());
        graphics.drawImage(tileSheet, pos[0], pos[1], 0, 0);

        graphics.disableClipRect();
        graphics.setScale(scale);

        graphics.render();
    }

}