package com.physmo.garnettest.entitytest;

import com.physmo.garnet.entity.Entity;
import com.physmo.garnet.entity.EntityDrawer;
import com.physmo.garnet.spritebatch.Sprite2D;
import com.physmo.garnet.spritebatch.SpriteBatch;

public class BasicEntityDrawer implements EntityDrawer {
    Entity parent;

    @Override
    public void injectParent(Entity parent) {
        this.parent = parent;
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.add(Sprite2D.build((int) parent.position.x, (int) parent.position.y, 16, 16, 0, 0, 16, 16));
    }
}
