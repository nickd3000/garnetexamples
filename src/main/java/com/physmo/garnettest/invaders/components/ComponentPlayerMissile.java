package com.physmo.garnettest.invaders.components;

import com.physmo.garnet.collision.BoxCollider2D;
import com.physmo.garnet.collision.CollisionPacket;
import com.physmo.garnet.entity.Component;

public class ComponentPlayerMissile extends Component {
    double speed = 250;

    @Override
    public void init() {
        BoxCollider2D boxCollider2D = new BoxCollider2D();
        boxCollider2D.setValues(parent, -5,-5,10,10);
        parent.addCollider(boxCollider2D);
    }

    @Override
    public void tick(double delta) {
        parent.position.y -= speed * delta;

        if (parent.position.y < 0) parent.setActive(false);
    }

    @Override
    public void onCollisionStart(CollisionPacket collisionPacket) {
        System.out.println("player missile collision");
        parent.setActive(false);
    }
}
