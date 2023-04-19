package com.physmo.garnetexamples.cellsurvivor.components;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnetexamples.cellsurvivor.Resources;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.SceneManager;
import com.physmo.garnettoolkit.Vector3;
import com.physmo.garnettoolkit.simplecollision.Collidable;
import com.physmo.garnettoolkit.simplecollision.ColliderComponent;
import com.physmo.garnettoolkit.simplecollision.CollisionSystem;
import com.physmo.garnettoolkit.simplecollision.RelativeObject;

import java.util.ArrayList;
import java.util.List;

public class ComponentEnemy extends Component {

    Resources resources;
    Graphics graphics;
    Garnet garnet;
    GameObject gameObjectLevel;
    LevelMap levelMap;

    Vector3 moveDir = new Vector3(1, 0, 0);
    GameObject player;

    double moveDirTimeout = 0;

    double health = 100;
    List<RelativeObject> closeObjects = new ArrayList<>();

    @Override
    public void tick(double t) {
        double speed = 10;

        parent.getTransform().addi(moveDir.scale(speed * t));

        moveDirTimeout -= t;
        if (moveDirTimeout < 0) {
            moveDirTimeout = 0.3;
            calculateMoveDir();
        }

        double minDist = 20;
        double pushForce = 1;
        for (RelativeObject closeObject : closeObjects) {

            if (closeObject.distance > minDist) continue;
            Vector3 transform = parent.getTransform();
            transform.x += closeObject.dx * t * pushForce;
            transform.y += closeObject.dy * t * pushForce;
        }
        closeObjects.clear();

        if (health < 0) {
            CollisionSystem collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);
            Collidable collidable = parent.getComponent(ColliderComponent.class);
            collisionSystem.removeCollidable(collidable);
            parent.destroy();
        }
    }

    private void calculateMoveDir() {
        moveDir = player.getTransform().getDirectionTo(parent.getTransform());
    }

    @Override
    public void init() {
        resources = parent.getContext().getObjectByType(Resources.class);

        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        graphics = garnet.getGraphics();

        gameObjectLevel = parent.getContext().getObjectByTag("levelmap");
        levelMap = parent.getContext().getComponent(LevelMap.class);

        player = parent.getContext().getObjectByTag("player");

        ColliderComponent collider = parent.getComponent(ColliderComponent.class);
        collider.setCallbackProximity(relativeObject -> {
            closeObjects.add(relativeObject); // Just store for now and process the event in the tick function.
        });
        collider.setCallbackEnter(target -> {
            if (target.hasTag("bullet")) {
                health -= 35;
            }
        });


    }

    @Override
    public void draw() {
        int x = (int) parent.getTransform().x;
        int y = (int) parent.getTransform().y;

        int[] tp = levelMap.getTileGridDrawer().translateMapToScreenPosition(x, y);
        int clipRectId = levelMap.getTileGridDrawer().getClipRectId();

        graphics.setActiveClipRect(clipRectId);
        graphics.drawImage(resources.getSpritesTilesheet(), tp[0], tp[1], 5, 0);
    }
}
