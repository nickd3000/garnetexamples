package com.physmo.garnetexamples.games.cellsurvivor.components;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnetexamples.games.cellsurvivor.Resources;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.scene.SceneManager;

public class SpriteHelper extends Component {
    Resources resources;
    Graphics graphics;
    Garnet garnet;
    GameObject gameObjectLevel;
    ComponentLevelMap levelMap;

    public void drawSpriteInMap(double x, double y, double tileX, double tileY) {
        int[] tp = levelMap.getTileGridDrawer().translateMapToScreenPosition(x, y);
        int clipRectId = levelMap.getTileGridDrawer().getClipRectId();

        graphics.setActiveClipRect(clipRectId);
        graphics.drawImage(resources.getSpritesTilesheet(), tp[0], tp[1], (int) tileX, (int) tileY);
    }

    public void drawSpriteInMap(int x, int y, int tileX, int tileY, double angle) {
        int[] tp = levelMap.getTileGridDrawer().translateMapToScreenPosition(x, y);
        int clipRectId = levelMap.getTileGridDrawer().getClipRectId();

        graphics.setActiveClipRect(clipRectId);
        graphics.drawImage(resources.getSpritesTilesheet(), tp[0], tp[1], tileX, tileY, angle);
    }

    @Override
    public void init() {
        resources = parent.getContext().getObjectByType(Resources.class);

        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        graphics = garnet.getGraphics();

        gameObjectLevel = parent.getContext().getObjectByTag("levelmap");
        levelMap = parent.getContext().getComponent(ComponentLevelMap.class);
    }

    @Override
    public void tick(double t) {

    }

    @Override
    public void draw() {

    }
}
