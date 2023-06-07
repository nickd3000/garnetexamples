package com.physmo.garnetexamples.games.invaders.scenes;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.graphics.Texture;
import com.physmo.garnet.graphics.TileSheet;
import com.physmo.garnetexamples.games.invaders.GameData;
import com.physmo.garnetexamples.games.invaders.InvadersEntityFactory;
import com.physmo.garnetexamples.games.invaders.components.ComponentGameLogic;
import com.physmo.garnetexamples.games.invaders.components.ComponentHud;
import com.physmo.garnetexamples.games.invaders.components.ComponentPlayer;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.Rect;
import com.physmo.garnettoolkit.Vector3;
import com.physmo.garnettoolkit.color.ColorUtils;
import com.physmo.garnettoolkit.particle.ParticleManager;
import com.physmo.garnettoolkit.scene.Scene;
import com.physmo.garnettoolkit.scene.SceneManager;
import com.physmo.garnettoolkit.simplecollision.CollisionSystem;

public class SceneGame extends Scene {

    GameData gameData;
    Garnet garnet;
    Graphics graphics;

    boolean showCollisionInfo = false;
    CollisionSystem collisionSystem;
    TileSheet tileSheet;
    Texture texture;

    public SceneGame(String name) {
        super(name);
    }

    @Override
    public void init() {
        context.reset();

        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);

        String spriteSheetFileName = "space.png";

        texture = Texture.loadTexture(spriteSheetFileName);
        garnet.getGraphics().addTexture(texture);
        texture.setFilter(true);

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
        graphics.setBackgroundColor(ColorUtils.SUNSET_BLUE);
    }

    public void injectCollisionDrawer(CollisionSystem collisionSystem) {
        collisionSystem.setCollisionDrawingCallback(collidable -> {

            Rect rect = collidable.collisionGetRegion();
            float x = (float) rect.x;
            float y = (float) rect.y;
            float w = (float) rect.w;
            float h = (float) rect.h;

            graphics.setColor(ColorUtils.GREEN);
            graphics.drawRect(x, y, w, h);
        });
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

    public void initParticleManager() {
        ParticleManager particleManager = new ParticleManager(1000);
        particleManager.setParticleDrawer(p -> {
            float pAge = (float) (p.age / p.lifeTime);
            garnet.getGraphics().setColor(p.colorSupplier.getColor(pAge));
            garnet.getGraphics().drawImage(tileSheet, (int) (p.position.x) - 8,
                    (int) (p.position.y) - 8, 3, 0);
        });

        context.add(particleManager);
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
