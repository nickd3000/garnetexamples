package com.physmo.garnettest.invaders.components;


import com.physmo.garnet.spritebatch.Sprite2D;
import com.physmo.garnet.spritebatch.SpriteBatch;
import com.physmo.garnettoolkit.Component;

public class ComponentEnemyMissile extends Component {
    double speed = 100;

    SpriteBatch spriteBatch;

    public ComponentEnemyMissile() {

    }

    @Override
    public void init() {
        spriteBatch = parent.getContext().getObjectByType(SpriteBatch.class);
    }

    @Override
    public void tick(double delta) {
        parent.getTransform().y += speed * delta;
        if (parent.getTransform().y > 480) parent.setActive(false);
    }

    @Override
    public void draw() {
        spriteBatch.add(Sprite2D.build(
                (int) (parent.getTransform().x) - 8,
                (int) (parent.getTransform().y) - 8,
                16, 16, 16 * 4, 32, 16, 16).addColor(1, 0, 1, 1));
    }
}
