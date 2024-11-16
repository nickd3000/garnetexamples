package com.physmo.garnetexamples.games.dogmatrix.components;

import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.toolkit.Component;
import com.physmo.garnet.toolkit.scene.SceneManager;
import com.physmo.garnet.toolkit.simplecollision.ColliderComponent;
import com.physmo.garnet.toolkit.simplecollision.CollisionSystem;
import com.physmo.garnetexamples.games.dogmatrix.MovementDirection;
import com.physmo.garnetexamples.games.dogmatrix.Resources;

public class Bullet extends Component {

    MovementDirection movementDirection;
    Resources resources;
    LevelLogic levelLogic;
    double speed;
    double timeAlive = 0;
    double damageAmount = 1;

    public double getDamageAmount() {
        return damageAmount;
    }

    public void setDamageAmount(double damageAmount) {
        this.damageAmount = damageAmount;
    }

    public void setMovementDirection(MovementDirection movementDirection) {
        this.movementDirection = movementDirection;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setLevelLogic(LevelLogic levelLogic) {
        this.levelLogic = levelLogic;
    }

    @Override
    public void init() {
        resources = SceneManager.getSharedContext().getObjectByType(Resources.class);

        ColliderComponent colliderComponent = parent.getComponent(ColliderComponent.class);

//        colliderComponent.setCallbackEnter(target -> {
//            if (target.hasTag("player")) System.out.println("player");
//            parent.destroy();
//        });

    }

    public void destroy() {
        CollisionSystem collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);
        ColliderComponent colliderComponent = parent.getComponent(ColliderComponent.class);
        collisionSystem.removeCollidable(colliderComponent);
        parent.destroy();
    }

    @Override
    public void tick(double t) {
        parent.getTransform().x += speed * t * movementDirection.getHDir();
        parent.getTransform().y += speed * t * movementDirection.getVDir();
        timeAlive += t;
        if (timeAlive > 4) destroy();

        if (levelLogic.isSolid((int) ((parent.getTransform().x + 8) / 16),
                (int) ((parent.getTransform().y + 8) / 16))) {
            destroy();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setActiveViewport(1);

        g.drawImage(resources.spriteTileSheet, parent.getTransform().x, parent.getTransform().y, 3, 0);

    }
}
