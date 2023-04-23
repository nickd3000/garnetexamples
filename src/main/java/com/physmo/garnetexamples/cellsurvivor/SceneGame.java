package com.physmo.garnetexamples.cellsurvivor;

import com.physmo.garnet.Garnet;
import com.physmo.garnetexamples.cellsurvivor.components.ComponentPlayer;
import com.physmo.garnetexamples.cellsurvivor.components.LevelMap;
import com.physmo.garnetexamples.cellsurvivor.components.SpriteHelper;
import com.physmo.garnetexamples.cellsurvivor.components.weapons.Gun;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.Scene;
import com.physmo.garnettoolkit.SceneManager;
import com.physmo.garnettoolkit.simplecollision.ColliderComponent;
import com.physmo.garnettoolkit.simplecollision.CollisionSystem;
import com.physmo.garnettoolkit.simplecollision.RelativeObject;

import java.util.List;
import java.util.Random;

public class SceneGame extends Scene {

    Random random = new Random();
    GameObject player;
    CollisionSystem collisionSystem;
    Garnet garnet;
    public SceneGame(String name) {
        super(name);
    }

    @Override
    public void init() {
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);

        collisionSystem = new CollisionSystem("d");
        context.add(collisionSystem);

        GameObject levelMap = new GameObject("levelmap").addComponent(new LevelMap());
        levelMap.addTag("levelmap");
        context.add(levelMap);

        player = new GameObject("player").addComponent(new ComponentPlayer());
        player.addComponent(new Gun());
        ColliderComponent collider = new ColliderComponent();
        player.addComponent(collider);
        collisionSystem.addCollidable(collider);
        player.addTag(Constants.TAG_PLAYER);
        context.add(player);

        Resources resources = new Resources();
        resources.init(garnet.getGraphics());
        context.add(resources);

        for (int i = 0; i < 500; i++) {
            EntityFactory.addEnemy(context, collisionSystem, random.nextInt(400), random.nextInt(400));
        }

        GameObject spriteHelper = new GameObject("spriteHelper").addComponent(new SpriteHelper());
        context.add(spriteHelper);
    }

    @Override
    public void tick(double delta) {

        if (Math.random() < 0.01) {
            EntityFactory.addEnemy(context, collisionSystem, random.nextInt(400), random.nextInt(400));
        }

        collisionSystem.processCloseObjects(Constants.TAG_ENEMY, 20);


        List<RelativeObject> nearestEnemies = collisionSystem.getNearestObjects(Constants.TAG_ENEMY, (int) player.getTransform().x, (int) player.getTransform().y, 60);
        player.getComponent(ComponentPlayer.class).setNearestEnemies(nearestEnemies);

        List<RelativeObject> nearestCrystals = collisionSystem.getNearestObjects(Constants.TAG_CRYSTAL, (int) player.getTransform().x, (int) player.getTransform().y, 50);
        player.getComponent(ComponentPlayer.class).setNearestCrystals(nearestCrystals);

        garnet.getDebugDrawer().setUserString("collisions", String.valueOf(collisionSystem.getTestsPerFrame()));
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
