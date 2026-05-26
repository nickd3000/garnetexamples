package com.physmo.garnetexamples.games.dogmatrix.components;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.input.InputAction;
import com.physmo.garnet.toolkit.GameObject;
import com.physmo.garnet.toolkit.scene.SceneManager;
import com.physmo.garnet.toolkit.simplecollision.ColliderComponent;
import com.physmo.garnet.toolkit.simplecollision.CollisionSystem;
import com.physmo.garnetexamples.games.dogmatrix.EntityFactory;
import com.physmo.garnetexamples.games.dogmatrix.GridBasedMover;
import com.physmo.garnetexamples.games.dogmatrix.MovementDirection;
import com.physmo.garnetexamples.games.dogmatrix.PickupType;
import com.physmo.garnetexamples.games.dogmatrix.Resources;


public class Player extends GameObject {

    LevelLogic levelLogic;
    Resources resources;
    Garnet garnet;
    GridBasedMover gridBasedMover;
    int spriteDir = 0;
    EntityFactory entityFactory;
    double bulletFireCounter = 0;
    double bulletFirePeriod = 0.2;
    double fireRateMultiplier = 1;
    boolean fourWayGun = false;
    boolean backwardsWayGun = false;

    public Player(String name) {
        super(name);
    }

    @Override
    public void init() {
        super.init();
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        resources = SceneManager.getSharedContext().getObjectByType(Resources.class);
        levelLogic = context.getObjectByType(LevelLogic.class);
        entityFactory = context.getObjectByType(EntityFactory.class);
        gridBasedMover = new GridBasedMover(levelLogic);

        CollisionSystem collisionSystem = context.getObjectByType(CollisionSystem.class);

        ColliderComponent colliderComponent = collisionSystem.addNewColliderToGameObject(this);

        colliderComponent.setCallbackEnter(target -> {
            if (target.hasTag("pickup")) {
                Pickup pickup = target.getComponent(Pickup.class);
                if (pickup != null) {
                    broadcastMessage("PICKUP_COLLECTED", pickup.getPickupType());
                }
            }
        });

        addComponent(gridBasedMover);

        this.addTag("player");

        snapX();
        snapY();
    }

    @Override
    public void onMessage(String name, Object data) {
        if (name.equals("PICKUP_COLLECTED")) {
            PickupType pickupType = (PickupType) data;
            switch (pickupType) {
                case fire_rate_up:
                    fireRateMultiplier += 0.1;
                    break;
                case four_way_gun:
                    fourWayGun = true;
                    break;
                case backwards_gun:
                    backwardsWayGun = true;
                    break;
            }
        }
    }

    @Override
    public void tick(double t) {
        super.tick(t);

        bulletFireCounter += t * fireRateMultiplier;
        boolean shoot = false;

        if (bulletFireCounter >= bulletFirePeriod) {
            shoot = true;
            bulletFireCounter -= bulletFirePeriod;
        }

        if (shoot && fourWayGun) {
            entityFactory.createBullet((int) (transform.x), (int) (transform.y), MovementDirection.Up, levelLogic);
            entityFactory.createBullet((int) (transform.x), (int) (transform.y), MovementDirection.Down, levelLogic);
            entityFactory.createBullet((int) (transform.x), (int) (transform.y), MovementDirection.Left, levelLogic);
            entityFactory.createBullet((int) (transform.x), (int) (transform.y), MovementDirection.Right, levelLogic);
        } else if (shoot && gridBasedMover.getMovementDirection() != MovementDirection.Stopped) {
            entityFactory.createBullet((int) (transform.x), (int) (transform.y), gridBasedMover.getMovementDirection(), levelLogic);
        }

        if (garnet.getInput().isActionKeyPressed(InputAction.UP)) {
            gridBasedMover.setDestinationDirection(MovementDirection.Up);
        }
        if (garnet.getInput().isActionKeyPressed(InputAction.DOWN)) {
            gridBasedMover.setDestinationDirection(MovementDirection.Down);
        }
        if (garnet.getInput().isActionKeyPressed(InputAction.LEFT)) {
            gridBasedMover.setDestinationDirection(MovementDirection.Left);
            spriteDir = 0;
        }
        if (garnet.getInput().isActionKeyPressed(InputAction.RIGHT)) {
            gridBasedMover.setDestinationDirection(MovementDirection.Right);
            spriteDir = 1;
        }


    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setActiveViewport(1);

        g.drawImage(resources.spriteTileSheet, transform.x, transform.y, 0, spriteDir);
    }

    public void snapX() {
        transform.x = transform.x - ((transform.x - 8) % 16) + 8;
    }

    public void snapY() {
        transform.y = transform.y - ((transform.y - 8) % 16) + 8;
    }

    public boolean isCloseToTileCenter() {
        double threshHold = 0.15;
        double dx = Math.abs(((transform.x + 8) % 16) - 8);
        double dy = Math.abs(((transform.y + 8) % 16) - 8);

        //System.out.println(dx+" "+dy);
        if (dy > threshHold) return false;
        return !(dx > threshHold);
    }
}
