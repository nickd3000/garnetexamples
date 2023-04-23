package com.physmo.garnetexamples.cellsurvivor;

import com.physmo.garnetexamples.cellsurvivor.components.ComponentCrystal;
import com.physmo.garnetexamples.cellsurvivor.components.ComponentEnemy;
import com.physmo.garnettoolkit.Context;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.simplecollision.ColliderComponent;
import com.physmo.garnettoolkit.simplecollision.CollisionSystem;

public class EntityFactory {

    public static void addEnemy(Context context, CollisionSystem collisionSystem, int x, int y) {
        GameObject enemy = new GameObject("enemy").addComponent(new ComponentEnemy());
        enemy.getTransform().set(x, y, 0);
        enemy.addTag(Constants.TAG_ENEMY);

        ColliderComponent collider = new ColliderComponent();
        enemy.addComponent(collider);
        collisionSystem.addCollidable(collider);

        context.add(enemy);
    }

    public static void addCrystal(Context context, CollisionSystem collisionSystem, int x, int y) {
        GameObject entity = new GameObject("crystal").addComponent(new ComponentCrystal());
        entity.getTransform().set(x, y, 0);
        entity.addTag(Constants.TAG_CRYSTAL);

        ColliderComponent collider = new ColliderComponent();
        entity.addComponent(collider);
        collisionSystem.addCollidable(collider);

        context.add(entity);
    }
}
