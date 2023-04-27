package com.physmo.garnetexamples.invaders.scenes;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.Texture;
import com.physmo.garnet.Utils;
import com.physmo.garnet.drawablebatch.TileSheet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnetexamples.invaders.GameData;
import com.physmo.garnetexamples.invaders.InvadersEntityFactory;
import com.physmo.garnetexamples.invaders.components.ComponentGameLogic;
import com.physmo.garnetexamples.invaders.components.ComponentHud;
import com.physmo.garnetexamples.invaders.components.ComponentPlayer;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.Rect;
import com.physmo.garnettoolkit.Scene;
import com.physmo.garnettoolkit.SceneManager;
import com.physmo.garnettoolkit.Vector3;
import com.physmo.garnettoolkit.color.Color;
import com.physmo.garnettoolkit.particle.ParticleManager;
import com.physmo.garnettoolkit.simplecollision.CollisionSystem;

public class SceneGame extends Scene {

    Texture texture;
    GameData gameData;
    Garnet garnet;
    Graphics graphics;
    TileSheet tileSheet;
    boolean showCollisionInfo = false;
    CollisionSystem collisionSystem;

    public SceneGame(String name) {
        super(name);
    }

    @Override
    public void init() {
        context.reset();

        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);

        String spriteSheetFileName = "space.png";
        String spriteSheetFileNamePath = Utils.getPathForResource(this, spriteSheetFileName);

        texture = Texture.loadTexture(spriteSheetFileNamePath);
        garnet.getGraphics().addTexture(texture);

        tileSheet = new TileSheet(texture, 16, 16);
        context.add(tileSheet);

        gameData = SceneManager.getSharedContext().getObjectByType(GameData.class);
        gameData.currentScore = 0;
        gameData.lives = 3;

        collisionSystem = new CollisionSystem("collisionsystem");
        context.add(collisionSystem);

        if (showCollisionInfo) injectCollisionDrawer(collisionSystem);

        createEntities();

        initParticleManager();
        graphics = garnet.getGraphics();
        graphics.setBackgroundColor(Color.SUNSET_BLUE.toInt());
    }

    public void injectCollisionDrawer(CollisionSystem collisionSystem) {
        collisionSystem.setCollisionDrawingCallback(collidable -> {

            Rect rect = collidable.collisionGetRegion();
            float x = (float) rect.x;
            float y = (float) rect.y;
            float w = (float) rect.w;
            float h = (float) rect.h;

            graphics.setColor(Color.GREEN.toInt());
            graphics.drawRect(x, y, w, h);
        });
    }

    public void initParticleManager() {
        ParticleManager particleManager = new ParticleManager(100);
        particleManager.setParticleDrawer(p -> {
            float pAge = (float) (p.age / p.lifeTime);
            garnet.getGraphics().setColor(p.colorSupplier.getColor(pAge).toInt());
            garnet.getGraphics().drawImage(tileSheet, (int) (p.position.x) - 8,
                    (int) (p.position.y) - 8, 3, 0);
        });

        context.add(particleManager);
    }


    public void createEntities() {
        GameObject player = new GameObject("player");
        player.addComponent(new ComponentPlayer());
        player.setTransform(new Vector3(100, 200, 0));
        player.setActive(true);
        player.setVisible(true);

        context.add(player);


        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 8; x++) { //9
                InvadersEntityFactory.addEnemy(context, collisionSystem, 60 + x * 30, 50 + y * 30);
            }
        }

        GameObject gameLogic = new GameObject("game_logic");
        gameLogic.addTag("game_logic");
        gameLogic.addComponent(new ComponentGameLogic());


        gameLogic.addComponent(new ComponentHud());
        gameLogic.setActive(true);
        gameLogic.setVisible(true);
        context.add(gameLogic);
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
