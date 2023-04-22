package com.physmo.garnetexamples.cellsurvivor;

import com.physmo.garnet.Garnet;
import com.physmo.garnetexamples.cellsurvivor.components.ComponentPlayer;
import com.physmo.garnetexamples.cellsurvivor.components.LevelMap;
import com.physmo.garnetexamples.cellsurvivor.components.SpriteHelper;
import com.physmo.garnetexamples.cellsurvivor.components.weapons.Gun;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.Scene;
import com.physmo.garnettoolkit.SceneManager;
import com.physmo.garnettoolkit.simplecollision.CollisionSystem;
import com.physmo.garnettoolkit.simplecollision.RelativeObject;

import java.util.List;
import java.util.Random;

public class SceneGame extends Scene {

    Random random = new Random();
    GameObject player;
    CollisionSystem collisionSystem;

    public SceneGame(String name) {
        super(name);
    }

    @Override
    public void init() {
        Garnet garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);

        collisionSystem = new CollisionSystem("d");
        context.add(collisionSystem);

        GameObject levelMap = new GameObject("levelmap").addComponent(new LevelMap());
        levelMap.addTag("levelmap");
        context.add(levelMap);

        player = new GameObject("player").addComponent(new ComponentPlayer());
        player.addComponent(new Gun());
        player.addTag("player");
        context.add(player);

        Resources resources = new Resources();
        resources.init(garnet.getGraphics());
        context.add(resources);

        for (int i = 0; i < 100; i++) {
            EnemyFactory.addEnemy(context, collisionSystem, random.nextInt(400), random.nextInt(400));
        }

        GameObject spriteHelper = new GameObject("spriteHelper").addComponent(new SpriteHelper());
        context.add(spriteHelper);
    }

    @Override
    public void tick(double delta) {

        collisionSystem.processCloseObjects("enemy", 20);


        List<RelativeObject> nearestObjects = collisionSystem.getNearestObjects((int) player.getTransform().x, (int) player.getTransform().y, 50);
        player.getComponent(ComponentPlayer.class).setNearestObjects(nearestObjects);

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
