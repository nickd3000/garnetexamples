package com.physmo.garnettest.invaders.components;

import com.physmo.garnet.collision.BoxCollider2D;
import com.physmo.garnet.collision.CollisionPacket;
import com.physmo.garnet.entity.Component;

public class ComponentEnemy extends Component {
    @Override
    public void init() {
        BoxCollider2D boxCollider2D = new BoxCollider2D();
        boxCollider2D.setValues(parent, -7, -7, 14, 14);
        parent.addCollider(boxCollider2D);
    }

    @Override
    public void tick(double delta) {

        ComponentGameLogic gameLogic = getComponent(ComponentGameLogic.class);

        if (gameLogic != null) {
            if (gameLogic.dir) {
                parent.position.x += 10 * delta;
            } else {
                parent.position.x -= 10 * delta;
            }
        }
    }

    @Override
    public void onCollisionStart(CollisionPacket collisionPacket) {
        System.out.println("enemy collision");
        parent.setActive(false);
    }
}
