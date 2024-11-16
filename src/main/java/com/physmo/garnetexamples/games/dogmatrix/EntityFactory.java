package com.physmo.garnetexamples.games.dogmatrix;

import com.physmo.garnet.toolkit.Context;
import com.physmo.garnet.toolkit.GameObject;
import com.physmo.garnet.toolkit.simplecollision.CollisionSystem;
import com.physmo.garnetexamples.games.dogmatrix.components.Bullet;
import com.physmo.garnetexamples.games.dogmatrix.components.LevelLogic;
import com.physmo.garnetexamples.games.dogmatrix.components.Mole;
import com.physmo.garnetexamples.games.dogmatrix.components.Pickup;

public class EntityFactory {

    CollisionSystem collisionSystem;
    Context sceneContext;


    public EntityFactory(CollisionSystem collisionSystem, Context sceneContext) {
        this.collisionSystem = collisionSystem;
        this.sceneContext = sceneContext;
    }

    public void createPickup(int x, int y, PickupType pickupType) {
        GameObject pickup = new GameObject("pickup");
        pickup.addTag("pickup");
        pickup.addComponent(new Pickup(pickupType));
        pickup.getTransform().set(16 * x, 16 * y, 0);
        collisionSystem.addNewColliderToGameObject(pickup);
        sceneContext.add(pickup);
    }

    public void createBaddie(int x, int y, LevelLogic levelLogic) {
        GameObject baddie = new GameObject("baddie");
        baddie.addTag("baddie");
        baddie.addComponent(new GridBasedMover(levelLogic));
        baddie.addComponent(new Mole());
        baddie.getTransform().set(16 * x, 16 * y, 0);
        collisionSystem.addNewColliderToGameObject(baddie);
        sceneContext.add(baddie);
    }

    public void createBullet(int x, int y, MovementDirection direction, LevelLogic levelLogic) {
        GameObject bullet = new GameObject("bullet");
        bullet.addTag("playerBullet");
        Bullet bulletComponent = new Bullet();
        bulletComponent.setMovementDirection(direction);
        bulletComponent.setSpeed(16 * 10);
        bulletComponent.setLevelLogic(levelLogic);
        bullet.addComponent(bulletComponent);
        bullet.getTransform().set(x, y, 0);
        collisionSystem.addNewColliderToGameObject(bullet);
        sceneContext.add(bullet);
    }
}
