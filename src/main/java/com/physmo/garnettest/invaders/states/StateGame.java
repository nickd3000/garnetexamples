package com.physmo.garnettest.invaders.states;

import com.physmo.garnet.GameState;
import com.physmo.garnet.Garnet;
import com.physmo.garnet.Texture;
import com.physmo.garnet.Utils;
import com.physmo.garnet.Vec3;
import com.physmo.garnet.entity.Entity;
import com.physmo.garnet.spritebatch.SpriteBatch;
import com.physmo.garnettest.invaders.EnemyType;
import com.physmo.garnettest.invaders.GameData;
import com.physmo.garnettest.invaders.components.ComponentEnemy;
import com.physmo.garnettest.invaders.components.ComponentEnemyMissile;
import com.physmo.garnettest.invaders.components.ComponentGameLogic;
import com.physmo.garnettest.invaders.components.ComponentHud;
import com.physmo.garnettest.invaders.components.ComponentPlayer;
import com.physmo.garnettest.invaders.components.ComponentPlayerMissile;

public class StateGame extends GameState {

    Texture texture;
    SpriteBatch spriteBatch;
    GameData gameData;

    SubState subState;
    double subStateTimer;

    public StateGame(Garnet garnet, String name) {
        super(garnet, name);
    }

    @Override
    public void init(Garnet garnet) {


        String spriteSheetFileName = "/space.PNg";
        String spriteSheetFileNamePath = Utils.getPathForResource(this, spriteSheetFileName);

        texture = Texture.loadTexture(spriteSheetFileNamePath);
        spriteBatch = new SpriteBatch(texture);

        getParticleManager().setSpriteBatch(spriteBatch);

        gameData = (GameData) garnet.getSharedObject("game_data");
        gameData.currentScore = 0;
        gameData.lives = 3;

        // init substates
        subState = SubState.GETREADY;
        subStateTimer = 3;
        gameData.showGetReady = true;

        createEntities();
        setAllEntitiesPause(true);
    }

    public void setAllEntitiesPause(boolean pause) {
        for (Entity entity : getEntitiesByTag("pausable")) {
            entity.setPaused(pause);
        }


    }

    public void createEntities() {
        Entity player = new Entity("player", this);
        player.addComponent(new ComponentPlayer(spriteBatch));
        //player.addEntityDrawer(new RenderComponentPlayer(spriteBatch));
        player.position = new Vec3(100, 200, 0);
        player.setActive(true);
        player.setVisible(true);
        player.addTag("pausable");
        addEntity(player);

        // Player missile pool.
        for (int i = 0; i < 20; i++) {
            Entity missile = new Entity("player_missile", this);
            missile.addTag("player_missile");
            missile.setActive(false);
            missile.setVisible(true);
            missile.addComponent(new ComponentPlayerMissile(spriteBatch));
            //missile.addEntityDrawer(new RenderComponentPlayerMissile(spriteBatch));
            missile.addTag("pausable");
            addEntity(missile);
        }
        // Enemy missile pool.
        for (int i = 0; i < 35; i++) {
            Entity missile = new Entity("enemy_missile", this);
            missile.addTag("enemy_missile");
            missile.setActive(false);
            missile.setVisible(true);
            missile.addComponent(new ComponentEnemyMissile(spriteBatch));
            //missile.addEntityDrawer(new RenderComponentPlayerMissile(spriteBatch));
            missile.addTag("pausable");
            addEntity(missile);
        }

        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 9; x++) {
                Entity enemy = new Entity("enemy", this);

                EnemyType enemyType = EnemyType.basic;
                if (Math.random() < 0.3) enemyType = EnemyType.armoured;
                if (Math.random() < 0.3) enemyType = EnemyType.shooter;
                enemy.addComponent(new ComponentEnemy(enemyType, spriteBatch));

                //enemy.addEntityDrawer(new RenderComponentEnemy(spriteBatch));
                enemy.position = new Vec3(60 + x * 30, 50 + y * 30, 0);
                enemy.setActive(true);
                enemy.setVisible(true);
                enemy.addTag("pausable");
                addEntity(enemy);
            }
        }

        Entity gameLogic = new Entity("game_logic", this);
        gameLogic.addTag("game_logic");
        gameLogic.addComponent(new ComponentGameLogic());

        gameLogic.addComponent(new ComponentHud());
        gameLogic.setActive(true);
        gameLogic.setVisible(true);
        addEntity(gameLogic);
    }

    @Override
    public void tick(double delta) {
        subStateTimer -= delta;
        switch (subState) {
            case GETREADY:
                if (subStateTimer < 0) {
                    subState = SubState.RUNNING;
                    setAllEntitiesPause(false);
                    System.out.println("switching state");
                    gameData.showGetReady = false;
                }
                break;
            case RUNNING:
                break;
        }
    }

    @Override
    public void draw() {
        spriteBatch.render(2);
        spriteBatch.clear();
    }

    enum SubState {
        GETREADY, RUNNING
    }
}
