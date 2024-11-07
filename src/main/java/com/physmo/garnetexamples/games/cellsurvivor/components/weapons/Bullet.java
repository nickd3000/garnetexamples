package com.physmo.garnetexamples.games.cellsurvivor.components.weapons;

import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.toolkit.Component;
import com.physmo.garnet.toolkit.simplecollision.Collidable;
import com.physmo.garnet.toolkit.simplecollision.ColliderComponent;
import com.physmo.garnet.toolkit.simplecollision.CollisionSystem;
import com.physmo.garnetexamples.games.cellsurvivor.Constants;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentPlayerCapabilities;
import com.physmo.garnetexamples.games.cellsurvivor.components.SpriteHelper;

public class Bullet extends Component {

    double baseSpeed = 50;
    double dx = 0, dy = 0;
    boolean killMe = false;
    double age = 0;
    SpriteHelper spriteHelper;
    ColliderComponent colliderComponent;
    ComponentPlayerCapabilities playerCapabilities;

    public void setDirection(double x, double y) {
        dx = x;
        dy = y;
    }

    @Override
    public void init() {
        playerCapabilities = parent.getContext().getComponent(ComponentPlayerCapabilities.class);


        spriteHelper = parent.getContext().getComponent(SpriteHelper.class);

        colliderComponent = parent.getComponent(ColliderComponent.class);

        colliderComponent.setCallbackEnter(target -> {
            if (target.hasTag(Constants.TAG_ENEMY)) {
                killMe = true;
                //System.out.println("bullet hit enemy");
            }
        });


    }

    @Override
    public void tick(double t) {
        age += t;
        double speedAdjuster = playerCapabilities.getProjectileSpeedAdjuster();
        parent.getTransform().x += dx * t * baseSpeed * speedAdjuster;
        parent.getTransform().y += dy * t * baseSpeed * speedAdjuster;

        if (age > 5) killMe = true;

        if (killMe) {
            CollisionSystem collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);
            Collidable collidable = parent.getComponent(ColliderComponent.class);
            collisionSystem.removeCollidable(collidable);
            parent.destroy();
        }

        colliderComponent.setCollisionRegion(-2, -2, 4, 4);

    }

    @Override
    public void draw(Graphics g) {
        int x = (int) parent.getTransform().x;
        int y = (int) parent.getTransform().y;

        spriteHelper.drawSpriteInMap(x - 8, y - 8, 0, 2);
    }
}
