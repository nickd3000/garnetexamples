package com.physmo.garnetexamples.cellsurvivor.components;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.input.Input;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.SceneManager;
import com.physmo.garnettoolkit.simplecollision.RelativeObject;

import java.util.ArrayList;
import java.util.List;

public class ComponentPlayer extends Component {

    Garnet garnet;

    List<RelativeObject> nearestObjects = new ArrayList<>();
    SpriteHelper spriteHelper;

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
        spriteHelper = parent.getContext().getComponent(SpriteHelper.class);
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);

        parent.addTag("player");

    }

    @Override
    public void draw() {
        int x = (int) parent.getTransform().x;
        int y = (int) parent.getTransform().y;

        spriteHelper.drawSpriteInMap(x, y, 2, 0);
    }
}
