package com.physmo.garnetexamples.games.cellsurvivor;

import com.physmo.garnet.ColorUtils;
import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.graphics.Viewport;
import com.physmo.garnet.structure.Rect;
import com.physmo.garnet.toolkit.GameObject;
import com.physmo.garnet.toolkit.scene.Scene;
import com.physmo.garnet.toolkit.scene.SceneManager;
import com.physmo.garnet.toolkit.simplecollision.CollisionSystem;
import com.physmo.garnet.toolkit.simplecollision.RelativeObject;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentEnemySpawner;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentGameLogic;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentHud;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentLevelMap;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentPlayer;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentPlayerCapabilities;
import com.physmo.garnetexamples.games.cellsurvivor.components.SpriteHelper;
import com.physmo.garnetexamples.games.cellsurvivor.components.weapons.Gun;

import java.util.List;
import java.util.Random;

public class SceneGame extends Scene {

    Random random = new Random();
    GameObject player;
    CollisionSystem collisionSystem;
    Garnet garnet;
    ComponentLevelMap componentLevelMap;
    SpriteHelper spriteHelperComponent;
    GameObject gameLogic;
    ComponentPlayerCapabilities componentPlayerCapabilities;


    public SceneGame(String name) {
        super(name);
    }

    @Override
    public void init() {
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);

        collisionSystem = new CollisionSystem("d");
        context.add(collisionSystem);
        collisionSystem.setCollisionDrawingCallback(collidable -> {
            garnet.getGraphics().setDrawOrder(1);
            Rect rect = collidable.collisionGetRegion();
            float x = (float) rect.x;
            float y = (float) rect.y;
            float w = (float) rect.w;
            float h = (float) rect.h;

            garnet.getGraphics().setColor(ColorUtils.WHITE);
            garnet.getGraphics().drawRect(x, y, w, h);
        });

        GameObject levelMapObject = new GameObject("levelmap");
        componentLevelMap = new ComponentLevelMap();
        levelMapObject.addComponent(componentLevelMap);
        levelMapObject.addTag("levelmap");
        context.add(levelMapObject);

        player = new GameObject("player").addComponent(new ComponentPlayer());
        player.addComponent(new Gun());
        componentPlayerCapabilities = new ComponentPlayerCapabilities();
        player.addComponent(componentPlayerCapabilities);
        EntityFactory.addColliderToGameObject(collisionSystem, player);
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
        Viewport viewport1 = garnet.getGraphics().getViewportManager().getViewport(Constants.tileGridCameraId);
        viewport1.setWidth(garnet.getDisplay().getWindowWidth())
                .setHeight(garnet.getDisplay().getWindowHeight())
                .setWindowY(40)
                .setWindowX(10)
                .setClipActive(true)
                .setDrawDebugInfo(true)
                .setZoom(2.0);

        Viewport viewport2 = garnet.getGraphics().getViewportManager().getViewport(Constants.scorePanelCameraId);
        viewport2.setWidth(garnet.getDisplay().getWindowWidth())
                .setHeight(40)
                .setWindowY(0)
                .setWindowX(0)
                .setClipActive(true)
                .setDrawDebugInfo(true)
                .setZoom(2.0);

        garnet.getDebugDrawer().setVisible(true);

        gameLogic = new GameObject("gameLogic");
        gameLogic.addComponent(new ComponentGameLogic());
        context.add(gameLogic);
    }

    @Override
    public void tick(double delta) {

        collisionSystem.processCloseObjects(Constants.TAG_ENEMY, 20);

        List<RelativeObject> nearestEnemies = collisionSystem.getNearestObjects(Constants.TAG_ENEMY, (int) player.getTransform().x, (int) player.getTransform().y, 60);
        player.getComponent(ComponentPlayer.class).setNearestEnemies(nearestEnemies);

        double pickupRadius = 50 * componentPlayerCapabilities.getPickupRadiusMultiplier();
        List<RelativeObject> nearestCrystals = collisionSystem.getNearestObjects(Constants.TAG_CRYSTAL, (int) player.getTransform().x, (int) player.getTransform().y, pickupRadius);
        player.getComponent(ComponentPlayer.class).setNearestCrystals(nearestCrystals);

        garnet.getDebugDrawer().setUserString("collisions", String.valueOf(collisionSystem.getTestsPerFrame()));

    }


    @Override
    public void draw(Graphics g) {

    }

    @Override
    public void onMakeActive() {

    }

    @Override
    public void onMakeInactive() {

    }
}
