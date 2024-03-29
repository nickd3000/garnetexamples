package com.physmo.garnetexamples.games.invaders.components;


import com.physmo.garnet.Garnet;
import com.physmo.garnet.Utils;
import com.physmo.garnetexamples.games.invaders.Constants;
import com.physmo.garnetexamples.games.invaders.Resources;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.scene.SceneManager;
import com.physmo.garnettoolkit.simplecollision.ColliderComponent;
import com.physmo.garnettoolkit.simplecollision.CollisionSystem;

public class ComponentPlayerMissile extends Component {
    double speed = 250;

    Resources resources;
    Garnet garnet;
    int color = Utils.floatToRgb(1, 0, 1, 1);
    CollisionSystem collisionSystem;
    ColliderComponent colliderComponent;

    public ComponentPlayerMissile() {
    }

    @Override
    public void tick(double delta) {
        parent.getTransform().y -= speed * delta;
        colliderComponent.setCollisionRegion(-2, -5, 4, 10);
        if (parent.getTransform().y < 0) {
            killMe();
        }
    }

    public void killMe() {
        collisionSystem.removeCollidable(colliderComponent);
        parent.destroy();
    }

    @Override
    public void init() {

        collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);

        parent.addTag(Constants.PLAYER_MISSILE);

        resources = SceneManager.getSharedContext().getObjectByType(Resources.class);
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);

        colliderComponent = parent.getComponent(ColliderComponent.class);

        colliderComponent.setCallbackEnter(collision -> {
            if (collision.hasTag(Constants.ENEMY_TAG)) killMe();
        });
    }

    @Override
    public void draw() {
        if (!parent.isActive()) return;

        garnet.getGraphics().setColor(color);
        garnet.getGraphics().drawImage(resources.getSpriteTilesheet(), (int) (parent.getTransform().x) - 8,
                (int) (parent.getTransform().y) - 8, 4, 2);

    }

}
