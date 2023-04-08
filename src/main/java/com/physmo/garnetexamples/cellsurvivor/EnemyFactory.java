package com.physmo.garnetexamples.cellsurvivor;

import com.physmo.garnetexamples.cellsurvivor.components.ComponentEnemy;
import com.physmo.garnettoolkit.Context;
import com.physmo.garnettoolkit.GameObject;

public class EnemyFactory {

    public static void addEnemy(Context context, int x, int y) {
        GameObject enemy = new GameObject("enemy").addComponent(new ComponentEnemy());
        enemy.getTransform().set(x, y, 0);
        enemy.addTag("enemy");
        context.add(enemy);
    }

}
