package com.physmo.garnettest.invaders;

import com.physmo.garnet.GameState;
import com.physmo.garnet.Garnet;
import com.physmo.garnet.Texture;
import com.physmo.garnet.Utils;
import com.physmo.garnet.Vec3;
import com.physmo.garnet.entity.Entity;
import com.physmo.garnet.spritebatch.SpriteBatch;
import com.physmo.garnettest.invaders.components.ComponentEnemy;
import com.physmo.garnettest.invaders.components.ComponentEnemyMissile;
import com.physmo.garnettest.invaders.components.ComponentGameLogic;
import com.physmo.garnettest.invaders.components.ComponentPlayer;
import com.physmo.garnettest.invaders.components.ComponentPlayerMissile;
import com.physmo.garnettest.invaders.renderers.RenderComponentEnemy;
import com.physmo.garnettest.invaders.renderers.RenderComponentHud;
import com.physmo.garnettest.invaders.renderers.RenderComponentPlayer;
import com.physmo.garnettest.invaders.renderers.RenderComponentPlayerMissile;

public class StateMain extends GameState {

    private static String spriteSheetFileName = "/space.PNg";

    Texture texture;
    SpriteBatch spriteBatch;

    public StateMain(Garnet garnet, String name) {
        super(garnet, name);
    }

    @Override
    public void init(Garnet garnet) {
        String spriteSheetFileNamePath = Utils.getPathForResource(this, spriteSheetFileName);

        texture = Texture.loadTexture(spriteSheetFileNamePath);
        spriteBatch = new SpriteBatch(texture);

        getParticleManager().setSpriteBatch(spriteBatch);

        GameData gameData = (GameData) garnet.getGlobalObject("game_data");
        gameData.currentScore = 0;
        gameData.lives = 3;

        createEntities();
    }

    public void createEntities() {
        Entity player = new Entity("player", this);
        player.addComponent(new ComponentPlayer());
        player.addEntityDrawer(new RenderComponentPlayer(spriteBatch));
        player.position = new Vec3(100, 200, 0);
        player.setActive(true);
        player.setVisible(true);
        addEntity(player);

        // Player missile pool.
        for (int i = 0; i < 20; i++) {
            Entity missile = new Entity("player_missile", this);
            missile.addTag("player_missile");
            missile.setActive(false);
            missile.setVisible(true);
            missile.addComponent(new ComponentPlayerMissile());
            missile.addEntityDrawer(new RenderComponentPlayerMissile(spriteBatch));
            addEntity(missile);
        }
        // Enemy missile pool.
        for (int i = 0; i < 35; i++) {
            Entity missile = new Entity("enemy_missile", this);
            missile.addTag("enemy_missile");
            missile.setActive(false);
            missile.setVisible(true);
            missile.addComponent(new ComponentEnemyMissile());
            missile.addEntityDrawer(new RenderComponentPlayerMissile(spriteBatch));
            addEntity(missile);
        }

        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 9; x++) {
                Entity enemy = new Entity("enemy", this);
                enemy.addComponent(new ComponentEnemy());
                enemy.addEntityDrawer(new RenderComponentEnemy(spriteBatch));
                enemy.position = new Vec3(60 + x * 30, 50 + y * 30, 0);
                enemy.setActive(true);
                enemy.setVisible(true);
                addEntity(enemy);
            }
        }

        Entity gameLogic = new Entity("game_logic", this);
        gameLogic.addTag("game_logic");
        gameLogic.addComponent(new ComponentGameLogic());
        gameLogic.addEntityDrawer(new RenderComponentHud());
        gameLogic.setActive(true);
        gameLogic.setVisible(true);
        addEntity(gameLogic);
    }

    @Override
    public void tick(double delta) {
    }

    @Override
    public void draw() {
        spriteBatch.render(2);
        spriteBatch.clear();
    }
}
