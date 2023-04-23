package com.physmo.garnetexamples.cellsurvivor.components.weapons;

import com.physmo.garnetexamples.cellsurvivor.Constants;
import com.physmo.garnetexamples.cellsurvivor.components.SpriteHelper;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.simplecollision.Collidable;
import com.physmo.garnettoolkit.simplecollision.ColliderComponent;
import com.physmo.garnettoolkit.simplecollision.CollisionSystem;

public class Bullet extends Component {

    double speed = 80;
    double dx = 0, dy = 0;
    boolean killMe = false;
    double age = 0;
    SpriteHelper spriteHelper;

    public void setDirection(double x, double y) {
        dx = x;
        dy = y;
    }

    @Override
    public void init() {
        spriteHelper = parent.getContext().getComponent(SpriteHelper.class);

        ColliderComponent colliderComponent = parent.getComponent(ColliderComponent.class);

        colliderComponent.setCallbackEnter(target -> {
            if (target.hasTag(Constants.TAG_ENEMY)) {
                killMe = true;
            }
        });
    }

    @Override
    public void tick(double t) {
        age += t;
        parent.getTransform().x += dx * t * speed;
        parent.getTransform().y += dy * t * speed;

        if (age > 3) killMe = true;

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
