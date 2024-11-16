package com.physmo.garnetexamples.games.dogmatrix.components;

import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.toolkit.Component;
import com.physmo.garnet.toolkit.scene.SceneManager;
import com.physmo.garnet.toolkit.simplecollision.ColliderComponent;
import com.physmo.garnet.toolkit.simplecollision.CollisionSystem;
import com.physmo.garnetexamples.games.dogmatrix.GridBasedMover;
import com.physmo.garnetexamples.games.dogmatrix.MovementDirection;
import com.physmo.garnetexamples.games.dogmatrix.Resources;

public class Mole extends Component {

    Resources resources;
    GridBasedMover gridBasedMover;
    double health = 5;

    @Override
    public void init() {
        resources = SceneManager.getSharedContext().getObjectByType(Resources.class);

        gridBasedMover = parent.getComponent(GridBasedMover.class);
        gridBasedMover.setSpeed(16 * 2);

        ColliderComponent colliderComponent = parent.getComponent(ColliderComponent.class);

        colliderComponent.setCallbackEnter(target -> {
            if (target.hasTag("playerBullet")) {
                Bullet bullet = target.getComponent(Bullet.class);
                if (bullet != null) {
                    health -= bullet.getDamageAmount();
                    bullet.destroy();
                }

                if (health <= 0) destroy();
            }

        });
    }

    @Override
    public void tick(double t) {
        if (gridBasedMover.getMovementDirection() == MovementDirection.Stopped) {
            gridBasedMover.setDestinationDirection(getRandomDirection());
        }
    }

    public void destroy() {
        CollisionSystem collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);
        ColliderComponent colliderComponent = parent.getComponent(ColliderComponent.class);
        collisionSystem.removeCollidable(colliderComponent);
        parent.destroy();
    }

    public MovementDirection getRandomDirection() {
        int i = (int) (Math.random() * 10) % 4;
        if (i == 0) return MovementDirection.Up;
        if (i == 1) return MovementDirection.Down;
        if (i == 2) return MovementDirection.Left;
        if (i == 3) return MovementDirection.Right;
        return MovementDirection.Stopped;
    }

    @Override
    public void draw(Graphics g) {
        g.setActiveViewport(1);

        g.drawImage(resources.spriteTileSheet, parent.getTransform().x, parent.getTransform().y, 3, 1);

    }
}
