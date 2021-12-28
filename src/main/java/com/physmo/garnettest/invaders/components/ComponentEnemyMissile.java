package com.physmo.garnettest.invaders.components;

import com.physmo.garnet.collision.BoxCollider2D;
import com.physmo.garnet.collision.CollisionPacket;
import com.physmo.garnet.entity.Component;

public class ComponentEnemyMissile extends Component {
    double speed = 100;

    @Override
    public void init() {
        BoxCollider2D boxCollider2D = new BoxCollider2D();
        boxCollider2D.setValues(parent, -2, -4, 4, 8);
        parent.addCollider(boxCollider2D);
    }

    @Override
    public void tick(double delta) {
        parent.position.y += speed * delta;

        if (parent.position.y > 480) parent.setActive(false);
    }

    @Override
    public void onCollisionStart(CollisionPacket collisionPacket) {
        //System.out.println("enemy missile collision");
        //parent.setActive(false);
    }
}
