package com.physmo.garnetexamples.graphics.support;

import com.physmo.garnet.drawablebatch.TileSheet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.Vector3;

public class FloatingInvaderComponent extends Component {
    Vector3 velocity;

    int width;
    int height;
    TileSheet tileSheet;
    Graphics graphics;

    public FloatingInvaderComponent(int width, int height) {
        this.width = width;
        this.height = height;

    }

    @Override
    public void init() {
        tileSheet = parent.getContext().getObjectByType(TileSheet.class);
        graphics = parent.getContext().getObjectByType(Graphics.class);
        velocity = new Vector3();
        velocity.set(Math.random() - 0.5, Math.random() - 0.5, 0);
        parent.getTransform().set(Math.random() * 100, Math.random() * 100, 0);
    }

    @Override
    public void tick(double t) {

        Vector3 transform = parent.getTransform();
        transform.addi(velocity);

        // Screen wrapping
        if (transform.x > width) transform.x -= width;
        if (transform.x < 0) transform.x += width;
        if (transform.y > height) transform.y -= height;
        if (transform.y < 0) transform.y += height;
    }


    @Override
    public void draw() {
        if (graphics == null) return;
        Vector3 transform = parent.getTransform();
        graphics.drawImage(tileSheet, (int) transform.x, (int) transform.y, 2, 2);
    }
}
