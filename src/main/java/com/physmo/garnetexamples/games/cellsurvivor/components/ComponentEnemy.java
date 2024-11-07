package com.physmo.garnetexamples.games.cellsurvivor.components;

import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.structure.Vector3;
import com.physmo.garnet.toolkit.Component;
import com.physmo.garnet.toolkit.GameObject;
import com.physmo.garnet.toolkit.simplecollision.Collidable;
import com.physmo.garnet.toolkit.simplecollision.ColliderComponent;
import com.physmo.garnet.toolkit.simplecollision.CollisionSystem;
import com.physmo.garnet.toolkit.simplecollision.RelativeObject;
import com.physmo.garnetexamples.games.cellsurvivor.Constants;
import com.physmo.garnetexamples.games.cellsurvivor.EntityFactory;
import com.physmo.garnetexamples.games.cellsurvivor.Resources;

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
    Resources resources;
    ColliderComponent collider;
    double speed = 5;
    int[] sprite = new int[2];
    ComponentPlayerCapabilities playerCapabilities;
    ComponentGameLogic gameLogic;

    @Override
    public void init() {
        playerCapabilities = parent.getContext().getComponent(ComponentPlayerCapabilities.class);

        spriteHelper = parent.getContext().getComponent(SpriteHelper.class);

        player = parent.getContext().getObjectByTag(Constants.TAG_PLAYER);

        collider = parent.getComponent(ColliderComponent.class);
        collider.setCallbackProximity(relativeObject -> {
            closeObjects.add(relativeObject); // Just store for now and process the event in the tick function.
        });
        collider.setCallbackEnter(target -> {
            if (target.hasTag(Constants.TAG_BULLET)) {
                health -= 55 * playerCapabilities.getProjectilePowerAdjuster();
            }
        });

        resources = parent.getContext().getObjectByType(Resources.class);
        gameLogic = parent.getContext().getComponent(ComponentGameLogic.class);

        rollAngle = Math.random() * 360;

    }

    public void setDetails(double speed, double health, int spriteX, int spriteY) {
        this.speed = speed;
        this.health = health;
        this.sprite[0] = spriteX;
        this.sprite[1] = spriteY;
    }

    @Override
    public void tick(double t) {

        parent.getTransform().x += moveDir.x * speed * t;
        parent.getTransform().y += moveDir.y * speed * t;

        moveDirTimeout -= t;
        if (moveDirTimeout < 0) {
            moveDirTimeout = 0.3;
            calculateMoveDir();
        }

        double minDist = 15;
        double pushForce = 250;
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

            gameLogic.addToScore(100);

            if (Math.random() < 0.4 * playerCapabilities.getLuckMultiplier()) {
                EntityFactory.addCrystal(parent.getContext(), collisionSystem, (int) parent.getTransform().x, (int) parent.getTransform().y);
            }
        }

        rollAngle += t * speed;

        collider.setCollisionRegion(-6, -8, 12, 16);
    }

    private void calculateMoveDir() {
        moveDir = player.getTransform().getDirectionTo(parent.getTransform());
    }


    @Override
    public void draw(Graphics g) {
        if (spriteHelper == null) return;

        int x = (int) parent.getTransform().x;
        int y = (int) parent.getTransform().y;

        double rotation = Math.sin(rollAngle) * 10;

        spriteHelper.drawSpriteInMap(x, y, sprite[0], sprite[1], rotation);
    }
}
