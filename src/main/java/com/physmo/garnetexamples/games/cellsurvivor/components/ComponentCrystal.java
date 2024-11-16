package com.physmo.garnetexamples.games.cellsurvivor.components;

import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.structure.Vector3;
import com.physmo.garnet.toolkit.Component;
import com.physmo.garnet.toolkit.GameObject;
import com.physmo.garnet.toolkit.simplecollision.Collidable;
import com.physmo.garnet.toolkit.simplecollision.ColliderComponent;
import com.physmo.garnet.toolkit.simplecollision.CollisionSystem;

public class ComponentCrystal extends Component {
    SpriteHelper spriteHelper;
    boolean killMe = false;
    ColliderComponent collider;
    boolean homing = false;
    double dx = 0, dy = 0;
    GameObject player;

    public void setHoming(boolean homing) {
        if (this.homing) return;
        this.homing = homing;
        player = parent.getContext().getObjectByTag("player");
        Vector3 directionTo = player.getTransform().getDirectionTo(parent.getTransform());
        dx = -directionTo.x * 100;
        dy = -directionTo.y * 100;
    }

    @Override
    public void init() {
        spriteHelper = parent.getContext().getComponent(SpriteHelper.class);
        collider = parent.getComponent(ColliderComponent.class);
    }



    @Override
    public void tick(double t) {
        if (killMe) {
            CollisionSystem collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);
            Collidable collidable = parent.getComponent(ColliderComponent.class);
            collisionSystem.removeCollidable(collidable);
            parent.destroy();
        }

        if (homing) {
            Vector3 directionTo = player.getTransform().getDirectionTo(parent.getTransform());
            dx += directionTo.x * 255 * t;
            dy += directionTo.y * 255 * t;
            parent.getTransform().x += dx * t;
            parent.getTransform().y += dy * t;
        }


        collider.setCollisionRegion(-3, -3, 6, 6);
    }

    @Override
    public void draw(Graphics g) {
        int x = (int) parent.getTransform().x;
        int y = (int) parent.getTransform().y;

        spriteHelper.drawSpriteInMap(x - 8, y - 8, 7, 1);
    }

    public void requestKill() {
        killMe = true;
    }
}
