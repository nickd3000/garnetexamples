package com.physmo.garnetexamples.cellsurvivor;

import com.physmo.garnet.Garnet;
import com.physmo.garnetexamples.cellsurvivor.components.ComponentBucketGrid;
import com.physmo.garnetexamples.cellsurvivor.components.ComponentEnemySpacer;
import com.physmo.garnetexamples.cellsurvivor.components.ComponentPlayer;
import com.physmo.garnetexamples.cellsurvivor.components.LevelMap;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.Scene;
import com.physmo.garnettoolkit.SceneManager;

import java.util.Random;

public class SceneGame extends Scene {

    Random random = new Random();

    public SceneGame(String name) {
        super(name);
    }

    @Override
    public void init() {
        Garnet garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);

        GameObject levelMap = new GameObject("levelmap").addComponent(new LevelMap());
        levelMap.addTag("levelmap");
        context.add(levelMap);

        GameObject player = new GameObject("player").addComponent(new ComponentPlayer());
        player.addTag("player");
        context.add(player);

        Resources resources = new Resources();
        resources.init(garnet.getGraphics());
        context.add(resources);

        GameObject bucketGrid = new GameObject("bucketGrid").addComponent(new ComponentBucketGrid());
        bucketGrid.addTag("bucketGrid");
        context.add(bucketGrid);

        for (int i = 0; i < 100; i++) {
            EnemyFactory.addEnemy(context, random.nextInt(400), random.nextInt(400));
        }


        GameObject enemySpacer = new GameObject("enemySpacer").addComponent(new ComponentEnemySpacer());
        player.addTag("enemySpacer");
        context.add(enemySpacer);
    }

    @Override
    public void tick(double delta) {

    }

    @Override
    public void draw() {

    }

    @Override
    public void onMakeActive() {

    }

    @Override
    public void onMakeInactive() {

    }
}
