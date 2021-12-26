package com.physmo.garnettest.invaders.renderers;

import com.physmo.garnet.entity.RenderComponent;
import com.physmo.garnet.spritebatch.Sprite2D;
import com.physmo.garnet.spritebatch.SpriteBatch;

public class RenderComponentPlayerMissile extends RenderComponent {

    SpriteBatch spriteBatch;

    public RenderComponentPlayerMissile(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    @Override
    public void draw() {
        if (parent.getActive()) // TODO: we should not need to do this
        spriteBatch.add(Sprite2D.build((int) (parent.position.x), (int) (parent.position.y), 16, 16, 16*4, 32, 16, 16));
    }
}
