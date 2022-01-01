package com.physmo.garnettest.invaders.renderers;

import com.physmo.garnet.entity.RenderComponent;
import com.physmo.garnet.spritebatch.Sprite2D;
import com.physmo.garnet.spritebatch.SpriteBatch;

public class RenderComponentEnemy extends RenderComponent {
    SpriteBatch spriteBatch;

    public RenderComponentEnemy(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    @Override
    public void draw() {
            spriteBatch.add(Sprite2D.build(
                    (int) (parent.position.x) - 8,
                    (int) (parent.position.y) - 8,
                    16, 16, 32, 32, 16, 16));
    }
}
