package com.physmo.garnetexamples.games.dogmatrix.components;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.toolkit.GameObject;
import com.physmo.garnet.toolkit.color.ColorSupplierLinear;
import com.physmo.garnet.toolkit.scene.SceneManager;
import com.physmo.garnetexamples.games.dogmatrix.Resources;

import java.util.HashMap;
import java.util.Map;

public class LevelLogic extends GameObject {

    int width = 18;
    int height = 13;

    LevelTile[] levelTiles;

    Garnet garnet;
    Resources resources;
    ColorSupplierLinear colorSupplier;
    double timer = 0;
    Map<Integer, String> levelData = new HashMap<>();

    public LevelLogic(String name) {
        super(name);
    }

    @Override
    public void init() {


        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        resources = SceneManager.getSharedContext().getObjectByType(Resources.class);

        int[] colors = new int[]{0xff00afFF, 0xa0ff00FF, 0xffff00FF, 0xFF0000FF, 0x00ffafFF, 0xaa00ffFF};
        colorSupplier = new ColorSupplierLinear(colors);


    }

    public void initLevelLayout() {
        setupLevelData();
        levelTiles = new LevelTile[width * height];
        for (int i = 0; i < width * height; i++) {
            levelTiles[i] = new LevelTile();
        }

        applyLevelDataToLevel(levelData.get(1));
    }

    public void setSolid(int x, int y, boolean solid) {
        int i = x + (width * y);
        levelTiles[i].solid = solid;
        levelTiles[i].tileId = 1;
    }

    @Override
    public void tick(double t) {
        timer += t;
    }

    @Override
    public void draw(Graphics g) {
        g.setActiveViewport(1);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int tileId = levelTiles[x + (y * width)].tileId;
                if (tileId == 1) {
                    g.setColor(getTileColor(x, y));
                    g.drawImage(resources.spriteTileSheet, x * 16, y * 16, 0, 2);
                }
            }
        }

    }

    public boolean isInBounds(int tileX, int tileY) {
        return tileX >= 0 && tileX < width && tileY >= 0 && tileY < height;
    }

    public boolean isSolid(int tileX, int tileY) {
        if (!isInBounds(tileX, tileY)) return true;
        int i = tileX + (width * tileY);
        return levelTiles[i].solid;
    }

    public void makeWall(int tileX, int tileY) {
        if (!isInBounds(tileX, tileY)) return;
        int i = tileX + (width * tileY);
        levelTiles[i].solid = true;
        levelTiles[i].tileId = 1;
    }

    public void growLevel(int x, int y) {
        for (int i = 0; i < 10; i++) {
            if (isSolid(x, y)) continue;
            int count = 0;

//            for (int yy = -1; yy <= 1; yy++) {
//                for (int xx = -1; xx <= 1; xx++) {
//                    if (!(xx == 0 && yy == 0) && isSolid(x + xx, y + yy)) count++;
//                }
//            }

            if (isSolid(x - 1, y)) count++;
            if (isSolid(x + 1, y)) count++;
            if (isSolid(x, y - 1)) count++;
            if (isSolid(x, y + 1)) count++;

            if (count == 2) makeWall(x, y);

        }
    }

    int getTileColor(int x, int y) {
        double offset = 0;//timer;
        offset += 1 + Math.sin((x / 20.0) + timer);
        offset += 1 + Math.cos((y / 20.0) + timer);
        return colorSupplier.getColor(offset / 4);
    }

    public void applyLevelDataToLevel(String ld) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = x + (y * width);
                if (ld.charAt(index) == '1') {
                    makeWall(x, y);
                }
            }
        }
    }

    public void setupLevelData() {
        String level1 = "";
        level1 += "111111111111111111";
        level1 += "1................1";
        level1 += "1.1111.1111.1111.1";
        level1 += "1....1.1....1....1";
        level1 += "1111.1.1111.1.1111";
        level1 += "1....1....1.1....1";
        level1 += "1.1111.1111.1....1";
        level1 += "1................1";
        level1 += "11111.1..........1";
        level1 += "1.....1..........1";
        level1 += "1.11111.1...1111.1";
        level1 += "1...1...1........1";
        level1 += "111111111111111111";

        levelData.put(1, level1);
    }

    public int[] getFreeSpace() {

        for (int i = 0; i < 20; i++) {
            int x = (int) (Math.random() * 14) + 1;
            int y = (int) (Math.random() * 14) + 1;
            if (!isSolid(x, y)) return new int[]{x, y};
        }

        return null;
    }
}
