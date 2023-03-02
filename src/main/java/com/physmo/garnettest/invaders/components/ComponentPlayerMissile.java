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

    public ComponentPlayerMissile(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }


    @Override
    public void init() {
        //BoxCollider2D boxCollider2D = new BoxCollider2D();
        //boxCollider2D.setValues(parent, -2, -4, 4, 8);
        //parent.addCollider(boxCollider2D);

        CollisionSystem collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);
        collisionSystem.addCollidable(this);

        parent.addTag(Constants.PLAYER_MISSILE);
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
                16, 16, 16 * 4, 32, 16, 16));
    }

    @Override
    public Rect collisionGetRegion() {
        Rect rect = new Rect(parent.getTransform().x - 5, parent.getTransform().y - 5, 10, 10);
        return rect;
    }

    @Override
    public void collisionCallback(CollisionPacket collisionPacket) {
        GameObject otherObject = collisionPacket.targetEntity.collisionGetGameObject();

        if (otherObject.getTags().contains("enemy")) {
            System.out.println("missile hit");
            parent.setActive(false);
        }
    }

    @Override
    public GameObject collisionGetGameObject() {
        return parent;
    }
}
