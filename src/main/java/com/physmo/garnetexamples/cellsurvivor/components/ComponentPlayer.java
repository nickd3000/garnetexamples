package com.physmo.garnetexamples.cellsurvivor.components;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.input.Input;
import com.physmo.garnetexamples.cellsurvivor.Resources;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.SceneManager;
import com.physmo.garnettoolkit.simplecollision.RelativeObject;

import java.util.ArrayList;
import java.util.List;

public class ComponentPlayer extends Component {

    Resources resources;
    Graphics graphics;
    Garnet garnet;
    GameObject gameObjectLevel;
    LevelMap levelMap;
    List<RelativeObject> nearestObjects = new ArrayList<>();

    public List<RelativeObject> getNearestObjects() {
        return nearestObjects;
    }

    public void setNearestObjects(List<RelativeObject> nearestObjects) {
        this.nearestObjects = nearestObjects;
    }

    @Override
    public void tick(double t) {
        double speed = 30;

        if (garnet.getInput().isPressed(Input.VirtualButton.RIGHT)) {
            parent.getTransform().x += speed * t;
        }
        if (garnet.getInput().isPressed(Input.VirtualButton.LEFT)) {
            parent.getTransform().x -= speed * t;
        }
        if (garnet.getInput().isPressed(Input.VirtualButton.UP)) {
            parent.getTransform().y -= speed * t;
        }
        if (garnet.getInput().isPressed(Input.VirtualButton.DOWN)) {
            parent.getTransform().y += speed * t;
        }

    }

    @Override
    public void init() {
        resources = parent.getContext().getObjectByType(Resources.class);

        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        graphics = garnet.getGraphics();

        parent.addTag("player");

        gameObjectLevel = parent.getContext().getObjectByTag("levelmap");
        levelMap = parent.getContext().getComponent(LevelMap.class);

    }

    @Override
    public void draw() {
        int x = (int) parent.getTransform().x;
        int y = (int) parent.getTransform().y;

        int[] tp = levelMap.getTileGridDrawer().translateMapToScreenPosition(x, y);

        graphics.drawImage(resources.getSpritesTilesheet(), tp[0], tp[1], 2, 0);
    }
}
