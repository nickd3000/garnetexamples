package com.physmo.garnettest.invaders.components;


import com.physmo.garnet.spritebatch.Sprite2D;
import com.physmo.garnet.spritebatch.SpriteBatch;
import com.physmo.garnettest.invaders.Constants;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.Rect;
import com.physmo.garnettoolkit.simplecollision.Collidable;
import com.physmo.garnettoolkit.simplecollision.CollisionPacket;
import com.physmo.garnettoolkit.simplecollision.CollisionSystem;

public class ComponentPlayerMissile extends Component implements Collidable {
    double speed = 250;

    SpriteBatch spriteBatch;

    public ComponentPlayerMissile() {
    }


    @Override
    public void init() {
        CollisionSystem collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);
        collisionSystem.addCollidable(this);

        parent.addTag(Constants.PLAYER_MISSILE);
        spriteBatch = parent.getContext().getObjectByType(SpriteBatch.class);
    }

    @Override
    public void tick(double delta) {
        parent.getTransform().y -= speed * delta;

        if (parent.getTransform().y < 0) parent.setActive(false);
    }


    @Override
    public void draw() {
        if (!parent.isActive()) return;
        spriteBatch.add(Sprite2D.build(
                (int) (parent.getTransform().x) - 8,
                (int) (parent.getTransform().y) - 8,
                16, 16, 16 * 4, 32, 16, 16).addColor(1, 0, 1, 1));
    }

    @Override
    public Rect collisionGetRegion() {
        Rect rect = new Rect(parent.getTransform().x - 5, parent.getTransform().y - 5, 10, 10);
        return rect;
    }

    @Override
    public void collisionCallback(CollisionPacket collisionPacket) {
        GameObject otherObject = collisionPacket.targetEntity.collisionGetGameObject();

        if (otherObject.getTags().contains(Constants.ENEMY_TAG)) {
            parent.setActive(false);
        }
    }

    @Override
    public GameObject collisionGetGameObject() {
        return parent;
    }
}
