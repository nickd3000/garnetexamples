package com.physmo.garnetexamples.games.cellsurvivor.components;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnetexamples.games.cellsurvivor.Constants;
import com.physmo.garnetexamples.games.cellsurvivor.Resources;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.scene.SceneManager;

public class SpriteHelper extends Component {
    Resources resources;
    Graphics g;
    Garnet garnet;
    GameObject gameObjectLevel;
    ComponentLevelMap levelMap;

    int spriteColor = 0xffffffff;

    public void drawSpriteInMap(double x, double y, double tileX, double tileY) {
        g.setActiveCamera(Constants.tileGridCameraId);
        g.setColor(spriteColor);
        g.drawImage(resources.getSpritesTilesheet(), x, y, (int) tileX, (int) tileY);
    }

    public void drawSpriteInMap(int x, int y, int tileX, int tileY, double angle) {
        g.setActiveCamera(Constants.tileGridCameraId);
        g.setColor(spriteColor);
        g.drawImage(resources.getSpritesTilesheet(), x, y, tileX, tileY, angle);
    }

    @Override
    public void init() {
        resources = parent.getContext().getObjectByType(Resources.class);

        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        g = garnet.getGraphics();

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
