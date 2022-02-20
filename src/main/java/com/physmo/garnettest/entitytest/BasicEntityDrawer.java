package com.physmo.garnettest.entitytest;

import com.physmo.garnet.entity.Component;
import com.physmo.garnet.spritebatch.Sprite2D;
import com.physmo.garnet.spritebatch.SpriteBatch;

public class BasicEntityDrawer extends Component {

    SpriteBatch spriteBatch;

    public BasicEntityDrawer(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    @Override
    public void init() {

    }

    @Override
    public void tick(double delta) {

    }

    @Override
    public void draw() {
        Sprite2D sprite2D = Sprite2D.build((int) parent.position.x, (int) parent.position.y, 16, 16, 0, 0, 16, 16);
        spriteBatch.add(sprite2D);
    }
}
