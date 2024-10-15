package com.physmo.garnetexamples.games.molepanic.components;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.toolkit.GameObject;
import com.physmo.garnet.toolkit.scene.SceneManager;
import com.physmo.garnetexamples.games.molepanic.Resources;

public class LevelLogic extends GameObject {

    int width = 12;
    int height = 12;

    LevelTile[] levelTiles;

    Garnet garnet;
    Resources resources;

    public LevelLogic(String name) {
        super(name);
    }

    @Override
    public void init() {
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        resources = SceneManager.getSharedContext().getObjectByType(Resources.class);
        levelTiles = new LevelTile[width * height];
        for (int i = 0; i < width * height; i++) {
            levelTiles[i] = new LevelTile();
        }
    }

    @Override
    public void tick(double t) {

    }

    @Override
    public void draw(Graphics g) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int tileId = levelTiles[x + (y * width)].tileId;
                garnet.getGraphics().drawImage(resources.spriteTileSheet, x * 16, y * 16, 0, 0);
            }
        }

    }
}
