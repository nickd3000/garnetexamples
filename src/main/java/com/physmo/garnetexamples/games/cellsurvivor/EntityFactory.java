package com.physmo.garnetexamples.games.cellsurvivor;

import com.physmo.garnet.toolkit.Context;
import com.physmo.garnet.toolkit.GameObject;
import com.physmo.garnet.toolkit.simplecollision.ColliderComponent;
import com.physmo.garnet.toolkit.simplecollision.CollisionSystem;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentCrystal;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentEnemy;

public class EntityFactory {

    public static void addEnemy(Context context, CollisionSystem collisionSystem, int x, int y) {
        GameObject enemy = new GameObject("enemy").addComponent(new ComponentEnemy());
        enemy.getTransform().set(x, y, 0);
        enemy.addTag(Constants.TAG_ENEMY);

        addColliderToGameObject(collisionSystem, enemy);

        context.add(enemy);
    }

    public static void addColliderToGameObject(CollisionSystem collisionSystem, GameObject gameObject) {
        ColliderComponent collider = new ColliderComponent();
        gameObject.addComponent(collider);
        collisionSystem.addCollidable(collider);
    }

    public static void addCrystal(Context context, CollisionSystem collisionSystem, int x, int y) {
        GameObject entity = new GameObject("crystal").addComponent(new ComponentCrystal());
        entity.getTransform().set(x, y, 0);
        entity.addTag(Constants.TAG_CRYSTAL);

        addColliderToGameObject(collisionSystem, entity);

        context.add(entity);
    }

}
