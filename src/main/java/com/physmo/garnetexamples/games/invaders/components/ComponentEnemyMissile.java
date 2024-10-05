package com.physmo.garnetexamples.games.invaders.components;


import com.physmo.garnet.Garnet;
import com.physmo.garnet.Utils;
import com.physmo.garnet.toolkit.Component;
import com.physmo.garnet.toolkit.scene.SceneManager;
import com.physmo.garnet.toolkit.simplecollision.ColliderComponent;
import com.physmo.garnet.toolkit.simplecollision.CollisionSystem;
import com.physmo.garnetexamples.games.invaders.Resources;

public class ComponentEnemyMissile extends Component {
    double speed = 100;

    Garnet garnet;
    int color = Utils.floatToRgb(1, 0, 1, 1);
    CollisionSystem collisionSystem;
    ColliderComponent colliderComponent;
    Resources resources;

    public ComponentEnemyMissile() {

    }

    @Override
    public void tick(double delta) {
        parent.getTransform().y += speed * delta;
        if (parent.getTransform().y > 480 / 2) {
            killMe();
        }
        colliderComponent.setCollisionRegion(-2, -5, 4, 10);
    }

    @Override
    public void draw() {
        if (!parent.isActive()) return;

        garnet.getGraphics().setColor(color);
        garnet.getGraphics().drawImage(resources.getSpriteTilesheet(), (int) (parent.getTransform().x) - 8,
                (int) (parent.getTransform().y) - 8, 4, 2);

    }

    public void killMe() {
        collisionSystem.removeCollidable(parent.getComponent(ColliderComponent.class));
        parent.destroy();
    }

    @Override
    public void init() {
        collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);
        resources = SceneManager.getSharedContext().getObjectByType(Resources.class);
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        colliderComponent = parent.getComponent(ColliderComponent.class);
    }

}
