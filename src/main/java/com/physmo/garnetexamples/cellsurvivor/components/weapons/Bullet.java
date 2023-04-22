package com.physmo.garnetexamples.cellsurvivor.components.weapons;

import com.physmo.garnetexamples.cellsurvivor.components.SpriteHelper;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.simplecollision.Collidable;
import com.physmo.garnettoolkit.simplecollision.ColliderComponent;
import com.physmo.garnettoolkit.simplecollision.CollisionSystem;

public class Bullet extends Component {

    double speed = 80;
    double dx = 0, dy = 0;
    boolean killMe = false;

    public void setDirection(double x, double y) {
        dx = x;
        dy = y;
    }

    SpriteHelper spriteHelper;
    @Override
    public void init() {
        spriteHelper = parent.getContext().getComponent(SpriteHelper.class);

        ColliderComponent colliderComponent = parent.getComponent(ColliderComponent.class);

        colliderComponent.setCallbackEnter(target -> {
            if (target.hasTag("enemy")) {
                killMe = true;
            }
        });
    }

    @Override
    public void tick(double t) {
        parent.getTransform().x += dx * t * speed;
        parent.getTransform().y += dy * t * speed;

        if (killMe) {
            CollisionSystem collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);
            Collidable collidable = parent.getComponent(ColliderComponent.class);
            collisionSystem.removeCollidable(collidable);
            parent.destroy();
        }
    }

    @Override
    public void draw() {
        int x = (int) parent.getTransform().x;
        int y = (int) parent.getTransform().y;

        spriteHelper.drawSpriteInMap(x, y, 1, 1);
    }
}
