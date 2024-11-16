package com.physmo.garnetexamples.games.cellsurvivor.scenes;

import com.physmo.garnet.ColorUtils;
import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.graphics.Viewport;
import com.physmo.garnet.input.InputKeys;
import com.physmo.garnet.structure.Rect;
import com.physmo.garnet.toolkit.GameObject;
import com.physmo.garnet.toolkit.particle.ParticleManager;
import com.physmo.garnet.toolkit.scene.Scene;
import com.physmo.garnet.toolkit.scene.SceneManager;
import com.physmo.garnet.toolkit.simplecollision.CollisionSystem;
import com.physmo.garnet.toolkit.simplecollision.RelativeObject;
import com.physmo.garnetexamples.games.cellsurvivor.Constants;
import com.physmo.garnetexamples.games.cellsurvivor.EntityFactory;
import com.physmo.garnetexamples.games.cellsurvivor.Resources;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentEnemySpawner;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentGameLogic;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentHud;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentLevelMap;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentPlayer;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentPlayerCapabilities;
import com.physmo.garnetexamples.games.cellsurvivor.components.ParticleFactory;
import com.physmo.garnetexamples.games.cellsurvivor.components.SpriteHelper;
import com.physmo.garnetexamples.games.cellsurvivor.components.weapons.Bow;
import com.physmo.garnetexamples.games.cellsurvivor.components.weapons.GlaveGun;
import com.physmo.garnetexamples.games.cellsurvivor.components.weapons.Wand;

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
    boolean showCollision = false;

    public SceneGame(String name) {
        super(name);
    }

    @Override
    public void init() {
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);

        initParticleManager();

        collisionSystem = new CollisionSystem("d");
        context.add(collisionSystem);
        collisionSystem.setCollisionDrawingCallback(collidable -> {
            if (showCollision) {
                garnet.getGraphics().setDrawOrder(Constants.DRAW_ORDER_DEBUG_OVERLAY);
                Rect rect = collidable.collisionGetRegion();
                float x = (float) rect.x;
                float y = (float) rect.y;
                float w = (float) rect.w;
                float h = (float) rect.h;

                garnet.getGraphics().setColor(ColorUtils.WHITE);
                garnet.getGraphics().drawRect(x, y, w, h);
            }
        });

        GameObject levelMapObject = new GameObject("levelmap");
        componentLevelMap = new ComponentLevelMap();
        levelMapObject.addComponent(componentLevelMap);
        levelMapObject.addTag("levelmap");
        context.add(levelMapObject);

        player = new GameObject("player").addComponent(new ComponentPlayer());
        player.addComponent(new Bow());
        player.addComponent(new Wand());
        player.addComponent(new GlaveGun());
        componentPlayerCapabilities = new ComponentPlayerCapabilities();
        player.addComponent(componentPlayerCapabilities);
        EntityFactory.addColliderToGameObject(collisionSystem, player);
        player.addTag(Constants.TAG_PLAYER);
        context.add(player);

//        Resources resources = new Resources();
//        resources.init(garnet.getGraphics());
//        context.add(resources);

        GameObject spriteHelper = new GameObject("spriteHelper");
        spriteHelperComponent = new SpriteHelper();
        spriteHelper.addComponent(spriteHelperComponent);
        context.add(spriteHelper);

        GameObject enemySpawnerObject = new GameObject("enemySpawner").addComponent(new ComponentEnemySpawner());
        context.add(enemySpawnerObject);

        GameObject hud = new GameObject("hud").addComponent(new ComponentHud());
        context.add(hud);

        // Configure cameras
        Viewport viewportOverlay = garnet.getGraphics().getViewportManager().getViewport(Constants.overlayViewportId);
        viewportOverlay.setWidth(garnet.getDisplay().getWindowWidth())
                .setHeight(garnet.getDisplay().getWindowHeight())
                .setWindowY(0)
                .setWindowX(0)
                .setClipActive(true)
                .setDrawDebugInfo(true)
                .setZoom(2.0);

        Viewport viewport1 = garnet.getGraphics().getViewportManager().getViewport(Constants.tileGridViewportId);
        viewport1.setWidth(garnet.getDisplay().getWindowWidth() - 20)
                .setHeight(garnet.getDisplay().getWindowHeight() - 50)
                .setWindowY(40)
                .setWindowX(10)
                .setClipActive(true)
                .setDrawDebugInfo(true)
                .setZoom(2.0);

        Viewport viewport2 = garnet.getGraphics().getViewportManager().getViewport(Constants.scorePanelViewportId);
        viewport2.setWidth(garnet.getDisplay().getWindowWidth())
                .setHeight(40)
                .setWindowY(0)
                .setWindowX(0)
                .setClipActive(true)
                .setDrawDebugInfo(true)
                .setZoom(2.0);

        garnet.getDebugDrawer().setVisible(false);

        GameObject particleFactory = new GameObject("particleFactory");
        particleFactory.addComponent(new ParticleFactory());
        context.add(particleFactory);

        gameLogic = new GameObject("gameLogic");
        gameLogic.addComponent(new ComponentGameLogic());
        context.add(gameLogic);
    }

    @Override
    public void tick(double delta) {

        collisionSystem.processCloseObjects(Constants.TAG_ENEMY, 20);

        List<RelativeObject> nearestEnemies = collisionSystem.getNearestObjects(Constants.TAG_ENEMY, (int) player.getTransform().x, (int) player.getTransform().y, 120);
        player.getComponent(ComponentPlayer.class).setNearestEnemies(nearestEnemies);

        double pickupRadius = (16 * 2) * componentPlayerCapabilities.getPickupRadiusMultiplier();
        List<RelativeObject> nearestCrystals = collisionSystem.getNearestObjects(Constants.TAG_CRYSTAL, (int) player.getTransform().x, (int) player.getTransform().y, pickupRadius);
        player.getComponent(ComponentPlayer.class).setNearestCrystals(nearestCrystals);

        garnet.getDebugDrawer().setUserString("collisions", String.valueOf(collisionSystem.getTestsPerFrame()));

        // Handle pause
        if (garnet.getInput().getKeyboard().isKeyFirstPress(InputKeys.KEY_P)) {
            SceneManager.getSceneByName("pause").ifPresent(scene -> ((ScenePause) scene).setPlayerCapabilities(componentPlayerCapabilities));
            SceneManager.pushSubScene("pause");
        }

        // Show level up screen
        if (garnet.getInput().getKeyboard().isKeyFirstPress(InputKeys.KEY_L)) {
            SceneManager.getSceneByName("levelUp").ifPresent(scene -> ((SceneLevelUp) scene).setPlayerCapabilities(componentPlayerCapabilities));
            SceneManager.pushSubScene("levelUp");
        }
    }

    public void initParticleManager() {
        Resources resources = SceneManager.getSharedContext().getObjectByType(Resources.class);

        ParticleManager particleManager = new ParticleManager(1000);
        particleManager.setParticleDrawer(p -> {
            float pAge = (float) (p.age / p.lifeTime);
            garnet.getGraphics().setColor(p.colorSupplier.getColor(pAge));
            garnet.getGraphics().setDrawOrder(Constants.DRAW_ORDER_ABOVE_GROUND);
            garnet.getGraphics().drawImage(resources.getSpritesTilesheet(), (int) (p.position.x) - 8,
                    (int) (p.position.y) - 8, 2, 1);
        });

        context.add(particleManager);
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
