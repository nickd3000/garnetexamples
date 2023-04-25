package com.physmo.garnetexamples.cellsurvivor.components;

import com.physmo.garnetexamples.cellsurvivor.Constants;
import com.physmo.garnetexamples.cellsurvivor.EntityFactory;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.Vector3;
import com.physmo.garnettoolkit.simplecollision.Collidable;
import com.physmo.garnettoolkit.simplecollision.ColliderComponent;
import com.physmo.garnettoolkit.simplecollision.CollisionSystem;
import com.physmo.garnettoolkit.simplecollision.RelativeObject;

import java.util.ArrayList;
import java.util.List;

public class ComponentEnemy extends Component {
    Vector3 moveDir = new Vector3(1, 0, 0);
    GameObject player;

    double moveDirTimeout = 0;

    double health = 100;
    List<RelativeObject> closeObjects = new ArrayList<>();

    SpriteHelper spriteHelper;
    double rollAngle = 0;

    @Override
    public void init() {

        spriteHelper = parent.getContext().getComponent(SpriteHelper.class);

        player = parent.getContext().getObjectByTag(Constants.TAG_PLAYER);

        ColliderComponent collider = parent.getComponent(ColliderComponent.class);
        collider.setCallbackProximity(relativeObject -> {
            closeObjects.add(relativeObject); // Just store for now and process the event in the tick function.
        });
        collider.setCallbackEnter(target -> {
            if (target.hasTag(Constants.TAG_BULLET)) {
                health -= 55;
            }
        });

        rollAngle = Math.random() * 360;
    }

    @Override
    public void tick(double t) {
        double speed = 10;

        parent.getTransform().addi(moveDir.scale(speed * t));

        moveDirTimeout -= t;
        if (moveDirTimeout < 0) {
            moveDirTimeout = 0.3;
            calculateMoveDir();
        }

        double minDist = 15;
        double pushForce = 150;
        for (RelativeObject closeObject : closeObjects) {


            if (closeObject.distance > minDist) continue;
            Vector3 transform = parent.getTransform();
            double dx = closeObject.dx / closeObject.distance;
            double dy = closeObject.dy / closeObject.distance;
            transform.x += dx * t * pushForce;
            transform.y += dy * t * pushForce;
        }
        closeObjects.clear();

        if (health < 0) {
            CollisionSystem collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);
            Collidable collidable = parent.getComponent(ColliderComponent.class);
            collisionSystem.removeCollidable(collidable);
            parent.destroy();

            EntityFactory.addCrystal(parent.getContext(), collisionSystem, (int) parent.getTransform().x, (int) parent.getTransform().y);
        }

        rollAngle += t * 20;
    }

    private void calculateMoveDir() {
        moveDir = player.getTransform().getDirectionTo(parent.getTransform());
    }



    @Override
    public void draw() {
        if (spriteHelper == null) return;

        int x = (int) parent.getTransform().x;
        int y = (int) parent.getTransform().y;

        double rotation = Math.sin(rollAngle) * 10;

        spriteHelper.drawSpriteInMap(x, y, 5, 0, rotation);
    }
}
