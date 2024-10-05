package com.physmo.garnetexamples.games.invaders;

import com.physmo.garnet.toolkit.Context;
import com.physmo.garnet.toolkit.GameObject;
import com.physmo.garnet.toolkit.Vector3;
import com.physmo.garnet.toolkit.simplecollision.ColliderComponent;
import com.physmo.garnet.toolkit.simplecollision.CollisionSystem;
import com.physmo.garnetexamples.games.invaders.components.ComponentEnemy;
import com.physmo.garnetexamples.games.invaders.components.ComponentEnemyMissile;
import com.physmo.garnetexamples.games.invaders.components.ComponentPlayerMissile;

public class InvadersEntityFactory {

    public static void addEnemy(Context context, CollisionSystem collisionSystem, double x, double y) {
        GameObject entity = new GameObject("enemy");

        EnemyType enemyType = EnemyType.basic;
        if (Math.random() < 0.3) enemyType = EnemyType.armoured;
        if (Math.random() < 0.3) enemyType = EnemyType.shooter;
        entity.addComponent(new ComponentEnemy(enemyType));

        addColliderToGameObject(collisionSystem, entity);

        entity.setTransform(new Vector3(x, y, 0));
        entity.setActive(true)
                .setVisible(true)
                .addTag(Constants.ENEMY_TAG);

        context.add(entity);
    }

    public static void addColliderToGameObject(CollisionSystem collisionSystem, GameObject gameObject) {
        ColliderComponent collider = new ColliderComponent();
        gameObject.addComponent(collider);
        collisionSystem.addCollidable(collider);
    }

    public static GameObject addPlayerMissile(Context context, CollisionSystem collisionSystem, double x, double y) {
        GameObject entity = new GameObject("player_missile");
        entity.setActive(false);
        entity.setVisible(true);
        entity.addComponent(new ComponentPlayerMissile());

        addColliderToGameObject(collisionSystem, entity);
        context.add(entity);
        entity.setActive(true);
        entity.getTransform().set(x, y, 0);

        return entity;

    }

    public static GameObject addEnemyMissile(Context context, CollisionSystem collisionSystem, double x, double y) {
        GameObject entity = new GameObject("enemy_missile");
        entity.setActive(false);
        entity.setVisible(true);
        entity.addComponent(new ComponentEnemyMissile());

        entity.addTag(Constants.ENEMY_MISSILE);

        addColliderToGameObject(collisionSystem, entity);

        context.add(entity);
        entity.setActive(true);
        entity.getTransform().set(x, y, 0);

        return entity;

    }
}
