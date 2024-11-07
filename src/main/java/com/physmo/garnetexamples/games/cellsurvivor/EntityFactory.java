package com.physmo.garnetexamples.games.cellsurvivor;

import com.physmo.garnet.toolkit.Context;
import com.physmo.garnet.toolkit.GameObject;
import com.physmo.garnet.toolkit.simplecollision.ColliderComponent;
import com.physmo.garnet.toolkit.simplecollision.CollisionSystem;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentCrystal;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentEnemy;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentGameLogic;

public class EntityFactory {

    public static void addEnemy(Context context, CollisionSystem collisionSystem, int x, int y) {
        ComponentEnemy enemyComponent = new ComponentEnemy();
        GameObject enemy = new GameObject("enemy");
        enemy.addComponent(enemyComponent);
        enemy.getTransform().set(x, y, 0);
        enemy.addTag(Constants.TAG_ENEMY);

        addColliderToGameObject(collisionSystem, enemy);

        ComponentGameLogic gameLogic = context.getComponent(ComponentGameLogic.class);


        configureEnemy(gameLogic, enemyComponent, 1);


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

    public static void configureEnemy(ComponentGameLogic gameLogic, ComponentEnemy enemy, int type) {
        int wave = gameLogic.getCurrentWave();

        //type = (int) ((Math.random() * 30)) % 5;

        type = wave;
        if (Math.random() > 0.8) type += 1;
        type = type % 5;

        // Mummy
        if (type == 0) {
            enemy.setDetails(5, 200, 10, 7);
        }

        // Snake
        if (type == 1) {
            enemy.setDetails(20, 20, 10, 3);
        }

        // Goblin
        if (type == 2) {
            enemy.setDetails(20, 50, 1, 6);
        }

        // Ogre 1
        if (type == 3) {
            enemy.setDetails(2, 250, 1, 8);
        }

        // Ogre 2
        if (type == 4) {
            enemy.setDetails(2, 350, 2, 8);
        }
    }
}
