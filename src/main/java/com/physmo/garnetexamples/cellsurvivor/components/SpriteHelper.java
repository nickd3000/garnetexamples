package com.physmo.garnetexamples.cellsurvivor.components;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnetexamples.cellsurvivor.Resources;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.SceneManager;

public class SpriteHelper extends Component {
    Resources resources;
    Graphics graphics;
    Garnet garnet;
    GameObject gameObjectLevel;
    LevelMap levelMap;

    public void drawSpriteInMap(int x, int y, int tileX, int tileY) {
        int[] tp = levelMap.getTileGridDrawer().translateMapToScreenPosition(x, y);
        int clipRectId = levelMap.getTileGridDrawer().getClipRectId();

        graphics.setActiveClipRect(clipRectId);
        graphics.drawImage(resources.getSpritesTilesheet(), tp[0], tp[1], tileX, tileY);
    }

    @Override
    public void init() {
        resources = parent.getContext().getObjectByType(Resources.class);

        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        graphics = garnet.getGraphics();

        gameObjectLevel = parent.getContext().getObjectByTag("levelmap");
        levelMap = parent.getContext().getComponent(LevelMap.class);
    }

    @Override
    public void tick(double t) {

    }

    @Override
    public void draw() {

    }
}
