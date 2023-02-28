package com.physmo.garnettest.invaders.states;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.Texture;
import com.physmo.garnet.Utils;
import com.physmo.garnet.spritebatch.SpriteBatch;
import com.physmo.garnettest.invaders.EnemyType;
import com.physmo.garnettest.invaders.GameData;
import com.physmo.garnettest.invaders.components.ComponentEnemy;
import com.physmo.garnettest.invaders.components.ComponentEnemyMissile;
import com.physmo.garnettest.invaders.components.ComponentGameLogic;
import com.physmo.garnettest.invaders.components.ComponentHud;
import com.physmo.garnettest.invaders.components.ComponentPlayer;
import com.physmo.garnettest.invaders.components.ComponentPlayerMissile;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.Scene;
import com.physmo.garnettoolkit.SceneManager;
import com.physmo.garnettoolkit.Vector3;
import com.physmo.garnettoolkit.simplecollision.CollisionSystem;

public class StateGame extends Scene {

    Texture texture;
    SpriteBatch spriteBatch;
    GameData gameData;

    SubState subState;
    double subStateTimer;
    Garnet garnet;


    public StateGame(String name, Garnet garnet) {
        super(name);
        this.garnet = garnet;
    }

    @Override
    public void init() {

        String spriteSheetFileName = "/space.PNg";
        String spriteSheetFileNamePath = Utils.getPathForResource(this, spriteSheetFileName);

        texture = Texture.loadTexture(spriteSheetFileNamePath);
        spriteBatch = new SpriteBatch(texture);

        //getParticleManager().setSpriteBatch(spriteBatch);

        gameData = SceneManager.getSharedContext().getObjectByType(GameData.class);
        //gameData = garnet.getSharedObject(GameData.class);
        gameData.currentScore = 0;
        gameData.lives = 3;

        // init substates
        subState = SubState.GETREADY;
        subStateTimer = 1;
        gameData.showGetReady = true;

        CollisionSystem collisionSystem = new CollisionSystem("collisionsystem");
        context.add(collisionSystem);

        createEntities();
        setAllEntitiesPause(true);
    }

    public void setAllEntitiesPause(boolean pause) {
//        for (Entity entity : getEntitiesByTag("pausable")) {
//            entity.setPaused(pause);
//        }


    }

    public void createEntities() {
        GameObject player = new GameObject("player");
        player.addComponent(new ComponentPlayer(spriteBatch, garnet));
        player.setTransform(new Vector3(100, 200, 0));
        player.setActive(true);
        player.setVisible(true);
        player.addTag("pausable");
        context.add(player);

        // Player missile pool.
        for (int i = 0; i < 20; i++) {
            GameObject missile = new GameObject("player_missile");
            missile.addTag("player_missile");
            missile.setActive(false);
            missile.setVisible(true);
            missile.addComponent(new ComponentPlayerMissile(spriteBatch));
            missile.addTag("pausable");
            context.add(missile);
        }
        // Enemy missile pool.
        for (int i = 0; i < 35; i++) {
            GameObject missile = new GameObject("enemy_missile");
            missile.addTag("enemy_missile");
            missile.setActive(false);
            missile.setVisible(true);
            missile.addComponent(new ComponentEnemyMissile(spriteBatch));
            missile.addTag("pausable");
            context.add(missile);
        }

        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 9; x++) {
                GameObject enemy = new GameObject("enemy");

                EnemyType enemyType = EnemyType.basic;
                if (Math.random() < 0.3) enemyType = EnemyType.armoured;
                if (Math.random() < 0.3) enemyType = EnemyType.shooter;
                enemy.addComponent(new ComponentEnemy(enemyType, spriteBatch));

                enemy.setTransform(new Vector3(60 + x * 30, 50 + y * 30, 0));
                enemy.setActive(true);
                enemy.setVisible(true);
                enemy.addTag("pausable");
                enemy.addTag("enemy");
                context.add(enemy);
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

        subStateTimer -= delta;
        switch (subState) {
            case GETREADY:
                if (subStateTimer < 0) {
                    subState = SubState.RUNNING;
                    setAllEntitiesPause(false);
                    //System.out.println("switching state");
                    gameData.showGetReady = false;
                }
                break;
            case RUNNING:
                //System.out.println("running");
                break;
        }
    }

    @Override
    public void draw() {

        spriteBatch.render(2);
        spriteBatch.clear();
    }

    @Override
    public void onMakeActive() {

    }

    @Override
    public void onMakeInactive() {

    }

    enum SubState {
        GETREADY, RUNNING
    }
}
