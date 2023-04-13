package com.physmo.garnetexamples.collision;

import com.physmo.garnet.drawablebatch.TileSheet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.color.Color;
import com.physmo.garnettoolkit.simplecollision.ColliderComponent;

public class ComponentCollidingSprite extends Component {
    TileSheet tileSheet;
    Graphics graphics;

    int mode = 0;

    @Override
    public void init() {
        tileSheet = parent.getContext().getObjectByType(TileSheet.class);
        graphics = parent.getContext().getObjectByType(Graphics.class);

        ColliderComponent component = parent.getComponent(ColliderComponent.class);
        component.setCallbackEnter(target -> {
            mode = 2;
        });
        component.setCallbackContinue(target -> {
            if (target.getTags().contains("testobject")) {
                mode = 1;
            }
        });
        component.setCallbackLeave(target -> {
            mode = 3;
        });
    }

    @Override
    public void tick(double t) {

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
