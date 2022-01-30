package com.physmo.garnettest.invaders.components;

import com.physmo.garnet.collision.BoxCollider2D;
import com.physmo.garnet.collision.CollisionPacket;
import com.physmo.garnet.entity.Component;
import com.physmo.garnet.spritebatch.Sprite2D;
import com.physmo.garnet.spritebatch.SpriteBatch;

public class ComponentPlayerMissile extends Component {
    double speed = 250;

    SpriteBatch spriteBatch;

    public ComponentPlayerMissile(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }


    @Override
    public void init() {
        BoxCollider2D boxCollider2D = new BoxCollider2D();
        boxCollider2D.setValues(parent, -2, -4, 4, 8);
        parent.addCollider(boxCollider2D);
    }

    @Override
    public void tick(double delta) {
        parent.position.y -= speed * delta;

        if (parent.position.y < 0) parent.setActive(false);
    }

    @Override
    public void onCollisionStart(CollisionPacket collisionPacket) {
        //System.out.println("player missile collision");
        parent.setActive(false);
    }

    @Override
    public void draw() {
        spriteBatch.add(Sprite2D.build(
                (int) (parent.position.x) - 8,
                (int) (parent.position.y) - 8,
                16, 16, 16 * 4, 32, 16, 16));
    }
}
