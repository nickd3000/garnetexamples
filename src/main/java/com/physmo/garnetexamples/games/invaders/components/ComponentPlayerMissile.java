package com.physmo.garnetexamples.games.invaders.components;


import com.physmo.garnet.ColorUtils;
import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.toolkit.Component;
import com.physmo.garnet.toolkit.scene.SceneManager;
import com.physmo.garnet.toolkit.simplecollision.ColliderComponent;
import com.physmo.garnet.toolkit.simplecollision.CollisionSystem;
import com.physmo.garnetexamples.games.invaders.Constants;
import com.physmo.garnetexamples.games.invaders.Resources;

public class ComponentPlayerMissile extends Component {
    double speed = 250;

    Resources resources;
    Garnet garnet;
    int color = ColorUtils.floatToRgb(1, 0, 1, 1);
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
    public void draw(Graphics g) {
        if (!parent.isActive()) return;

        garnet.getGraphics().setColor(color);
        garnet.getGraphics().drawImage(resources.getSpriteTilesheet(), (int) (parent.getTransform().x) - 8,
                (int) (parent.getTransform().y) - 8, 4, 2);

    }

}
