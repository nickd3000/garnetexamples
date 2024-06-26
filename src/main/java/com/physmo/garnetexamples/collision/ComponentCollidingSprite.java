package com.physmo.garnetexamples.collision;

import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.graphics.TileSheet;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.Vector3;
import com.physmo.garnettoolkit.color.ColorUtils;
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
        component.setCallbackEnter(target -> mode = 2);
        component.setCallbackContinue(target -> {
            if (target.hasTag("testobject")) {
                mode = 1;
            }
        });
        component.setCallbackLeave(target -> mode = 3);
        component.setCallbackProximity(relativeObject -> {
            closeObjects.add(relativeObject); // Just store for now and process the event in the tick function.
        });
    }

    @Override
    public void tick(double t) {

        double minDist = 20;
        double pushForce = 80;
        for (RelativeObject closeObject : closeObjects) {

            if (closeObject.distance > minDist) continue;
            Vector3 transform = parent.getTransform();
            double dx = closeObject.dx / closeObject.distance;
            double dy = closeObject.dy / closeObject.distance;
            transform.x += dx * t * pushForce;
            transform.y += dy * t * pushForce;
        }
        closeObjects.clear();

    }

    @Override
    public void draw() {
        //graphics.setScale(3);
        if (mode == 0) graphics.setColor(ColorUtils.SUNSET_GREEN);
        if (mode == 1) graphics.setColor(ColorUtils.SUNSET_ORANGE);
        if (mode == 2) graphics.setColor(ColorUtils.SUNSET_RED);
        if (mode == 3) graphics.setColor(ColorUtils.SUNSET_BLUE);
        graphics.drawImage(tileSheet, (int) parent.getTransform().x, (int) parent.getTransform().y, 2, 2);
    }
}
