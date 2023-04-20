package com.physmo.garnetexamples.collision;

import com.physmo.garnet.drawablebatch.TileSheet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.Vector3;
import com.physmo.garnettoolkit.color.Color;
import com.physmo.garnettoolkit.simplecollision.ColliderComponent;
import com.physmo.garnettoolkit.simplecollision.RelativeObject;

import java.util.ArrayList;
import java.util.List;

public class ComponentCollidingSprite extends Component {
    TileSheet tileSheet;
    Graphics graphics;

    int mode = 0;

    List<RelativeObject> closeObjects = new ArrayList<>();

    @Override
    public void init() {
        tileSheet = parent.getContext().getObjectByType(TileSheet.class);
        graphics = parent.getContext().getObjectByType(Graphics.class);

        ColliderComponent component = parent.getComponent(ColliderComponent.class);
        component.setCallbackEnter(target -> {
            mode = 2;
        });
        component.setCallbackContinue(target -> {
            if (target.hasTag("testobject")) {
                mode = 1;
            }
        });
        component.setCallbackLeave(target -> {
            mode = 3;
        });
        component.setCallbackProximity(relativeObject -> {
            closeObjects.add(relativeObject); // Just store for now and process the event in the tick function.


        });
    }

    @Override
    public void tick(double t) {

        double minDist = 20;
        double pushForce = 3;
        for (RelativeObject closeObject : closeObjects) {

            if (closeObject.distance > minDist) continue;
            Vector3 transform = parent.getTransform();
            transform.x += closeObject.dx * t * pushForce;
            transform.y += closeObject.dy * t * pushForce;
        }
        closeObjects.clear();

    }

    @Override
    public void draw() {
        //graphics.setScale(3);
        if (mode == 0) graphics.setColor(Color.SUNSET_GREEN.toInt());
        if (mode == 1) graphics.setColor(Color.SUNSET_ORANGE.toInt());
        if (mode == 2) graphics.setColor(Color.SUNSET_RED.toInt());
        if (mode == 3) graphics.setColor(Color.SUNSET_BLUE.toInt());
        graphics.drawImage(tileSheet, (int) parent.getTransform().x, (int) parent.getTransform().y, 2, 2);
    }
}