package com.physmo.garnetexamples.invaders.components;


import com.physmo.garnet.Garnet;
import com.physmo.garnet.Utils;
import com.physmo.garnet.spritebatch.TileSheet;
import com.physmo.garnetexamples.invaders.Constants;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.Rect;
import com.physmo.garnettoolkit.SceneManager;
import com.physmo.garnettoolkit.simplecollision.Collidable;
import com.physmo.garnettoolkit.simplecollision.CollisionPacket;
import com.physmo.garnettoolkit.simplecollision.CollisionSystem;

public class ComponentEnemyMissile extends Component implements Collidable {
    double speed = 100;


    TileSheet tileSheet;
    Garnet garnet;

    public ComponentEnemyMissile() {

    }
    int color = Utils.floatToRgb(1, 0, 1, 1);

    @Override
    public void tick(double delta) {
        parent.getTransform().y += speed * delta;
        if (parent.getTransform().y > 480) parent.setActive(false);
    }

    @Override
    public void init() {

        CollisionSystem collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);
        collisionSystem.addCollidable(this);
        tileSheet = parent.getContext().getObjectByType(TileSheet.class);
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
    }

    @Override
    public void draw() {
        if (!parent.isActive()) return;

        garnet.getGraphics().setColor(color);
        garnet.getGraphics().drawImage(tileSheet, (int) (parent.getTransform().x) - 8,
                (int) (parent.getTransform().y) - 8, 4, 2);

    }

    @Override
    public Rect collisionGetRegion() {
        Rect rect = new Rect(parent.getTransform().x - 5, parent.getTransform().y - 5, 10, 10);
        return rect;
    }

    @Override
    public void collisionCallback(CollisionPacket collisionPacket) {
        GameObject otherObject = collisionPacket.targetEntity.collisionGetGameObject();

        if (otherObject.getTags().contains(Constants.PLAYER_TAG)) {
            parent.setActive(false);
            parent.setVisible(false);
        }
    }

    @Override
    public GameObject collisionGetGameObject() {
        return parent;
    }
}
