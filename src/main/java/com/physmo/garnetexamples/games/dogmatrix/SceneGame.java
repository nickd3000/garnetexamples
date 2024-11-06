package com.physmo.garnetexamples.games.dogmatrix;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.graphics.Viewport;
import com.physmo.garnet.toolkit.scene.Scene;
import com.physmo.garnet.toolkit.scene.SceneManager;
import com.physmo.garnet.toolkit.simplecollision.CollisionSystem;
import com.physmo.garnetexamples.games.dogmatrix.components.LevelLogic;
import com.physmo.garnetexamples.games.dogmatrix.components.Player;


public class SceneGame extends Scene {

    CollisionSystem collisionSystem;


    public SceneGame(String name) {
        super(name);
    }


    @Override
    public void init() {
        context.reset();
        LevelLogic levelLogic = new LevelLogic("levelLogic");
        levelLogic.initLevelLayout();
        context.add(levelLogic);

        collisionSystem = new CollisionSystem("collisionsystem");
        context.add(collisionSystem);

        EntityFactory entityFactory = new EntityFactory(collisionSystem, context);
        context.add(entityFactory);

        Player player = new Player("player");
        player.getTransform().set(16, 16, 0);
        context.add(player);


        for (int i = 0; i < 8; i++) {
            int[] pos = levelLogic.getFreeSpace();
            if (i == 0) entityFactory.createPickup(pos[0], pos[1], PickupType.backwards_gun);
            else if (i == 1) entityFactory.createPickup(pos[0], pos[1], PickupType.fire_rate_up);
            else if (i == 2) entityFactory.createPickup(pos[0], pos[1], PickupType.four_way_gun);
            else if (i == 3) entityFactory.createPickup(pos[0], pos[1], PickupType.speed_up);
            else entityFactory.createPickup(pos[0], pos[1], PickupType.bone);
        }

        for (int i = 0; i < 3; i++) {
            int[] pos = levelLogic.getFreeSpace();
            entityFactory.createBaddie(pos[0], pos[1], levelLogic);
        }

        Garnet garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        garnet.getGraphics().setZoom(2);

        int scorePanelWidth = 140;
        int arenaWidth = 640 - scorePanelWidth;
        int arenaHeight = 480;


        // Configure cameras
        Viewport viewport1 = garnet.getGraphics().getViewportManager().getViewport(1);
        viewport1.setWidth(arenaWidth)
                .setHeight(arenaHeight)
                .setWindowY(0)
                .setWindowX(0)
                .setClipActive(true)
                .setDrawDebugInfo(true)
                .setZoom(1.0);

        Viewport viewport2 = garnet.getGraphics().getViewportManager().getViewport(2);
        viewport2.setWidth(scorePanelWidth)
                .setHeight(480)
                .setWindowY(0)
                .setWindowX(arenaWidth)
                .setClipActive(true)
                .setDrawDebugInfo(true)
                .setZoom(1.0);
    }

    @Override
    public void tick(double delta) {

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
