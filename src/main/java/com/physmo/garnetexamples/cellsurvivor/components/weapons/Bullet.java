package com.physmo.garnetexamples.cellsurvivor.components.weapons;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnetexamples.cellsurvivor.Resources;
import com.physmo.garnetexamples.cellsurvivor.components.LevelMap;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.SceneManager;
import com.physmo.garnettoolkit.simplecollision.ColliderComponent;

public class Bullet extends Component {
    Resources resources;
    Graphics graphics;
    Garnet garnet;
    GameObject gameObjectLevel;
    LevelMap levelMap;
    double speed = 50;
    double dx = 0, dy = 0;

    public void setDirection(double x, double y) {
        dx = x;
        dy = y;
    }

    @Override
    public void init() {
        resources = parent.getContext().getObjectByType(Resources.class);

        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        graphics = garnet.getGraphics();

        gameObjectLevel = parent.getContext().getObjectByTag("levelmap");
        levelMap = parent.getContext().getComponent(LevelMap.class);

        ColliderComponent colliderComponent = parent.getComponent(ColliderComponent.class);

    }

    @Override
    public void tick(double t) {
        parent.getTransform().x += dx * t * speed;
        parent.getTransform().y += dy * t * speed;
    }

    @Override
    public void draw() {
        int x = (int) parent.getTransform().x;
        int y = (int) parent.getTransform().y;

        int[] tp = levelMap.getTileGridDrawer().translateMapToScreenPosition(x, y);

        graphics.drawImage(resources.getSpritesTilesheet(), tp[0], tp[1], 1, 1);
    }
}