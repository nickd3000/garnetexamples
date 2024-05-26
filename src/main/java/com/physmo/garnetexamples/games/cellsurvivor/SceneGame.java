package com.physmo.garnetexamples.games.cellsurvivor;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Camera;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentEnemySpawner;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentHud;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentLevelMap;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentPlayer;
import com.physmo.garnetexamples.games.cellsurvivor.components.SpriteHelper;
import com.physmo.garnetexamples.games.cellsurvivor.components.weapons.Gun;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.scene.Scene;
import com.physmo.garnettoolkit.scene.SceneManager;
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
    ComponentLevelMap componentLevelMap;
    SpriteHelper spriteHelperComponent;

    public SceneGame(String name) {
        super(name);
    }

    @Override
    public void init() {
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);

        collisionSystem = new CollisionSystem("d");
        context.add(collisionSystem);

        GameObject levelMapObject = new GameObject("levelmap");
        componentLevelMap = new ComponentLevelMap();
        levelMapObject.addComponent(componentLevelMap);
        levelMapObject.addTag("levelmap");
        context.add(levelMapObject);

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

        GameObject spriteHelper = new GameObject("spriteHelper");
        spriteHelperComponent = new SpriteHelper();
        spriteHelper.addComponent(spriteHelperComponent);
        context.add(spriteHelper);

        GameObject enemySpawnerObject = new GameObject("enemySpawner").addComponent(new ComponentEnemySpawner());
        context.add(enemySpawnerObject);

        GameObject hud = new GameObject("hud").addComponent(new ComponentHud());
        context.add(hud);

        // Configure cameras
        Camera camera1 = garnet.getGraphics().getCameraManager().getCamera(Constants.tileGridCameraId);
        camera1.setWidth(garnet.getDisplay().getWindowWidth())
                .setHeight(garnet.getDisplay().getWindowHeight())
                .setWindowY(40)
                .setWindowX(10)
                .setClipActive(true)
                .setDrawDebugInfo(true)
                .setZoom(2.0);

        Camera camera2 = garnet.getGraphics().getCameraManager().getCamera(Constants.scorePanelCameraId);
        camera2.setWidth(garnet.getDisplay().getWindowWidth())
                .setHeight(40)
                .setWindowY(0)
                .setWindowX(0)
                .setClipActive(true)
                .setDrawDebugInfo(true)
                .setZoom(2.0);
    }

    @Override
    public void tick(double delta) {

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
