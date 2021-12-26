package com.physmo.garnettest.invaders;

import com.physmo.garnet.GameState;
import com.physmo.garnet.Garnet;
import com.physmo.garnet.Texture;
import com.physmo.garnet.Vec3;
import com.physmo.garnet.entity.Entity;
import com.physmo.garnet.spritebatch.SpriteBatch;
import com.physmo.garnettest.invaders.components.ComponentEnemy;
import com.physmo.garnettest.invaders.components.ComponentPlayer;
import com.physmo.garnettest.invaders.components.ComponentPlayerMissile;
import com.physmo.garnettest.invaders.renderers.RenderComponentEnemy;
import com.physmo.garnettest.invaders.renderers.RenderComponentPlayer;
import com.physmo.garnettest.invaders.renderers.RenderComponentPlayerMissile;

public class StateMain extends GameState {

    // TODO: Make this relative
    private static String spriteSheetFileName = "/Users/nick/Dev/java/garnettest/src/main/resources/space.PNG";

    Garnet garnet;
    Texture texture;
    SpriteBatch spriteBatch;

    @Override
    public void init(Garnet garnet) {
        this.garnet = garnet;
        texture = Texture.loadTexture(spriteSheetFileName);
        spriteBatch = new SpriteBatch(texture);

        createEntities();
    }

    public void createEntities() {
        Entity player = new Entity("player", this);
        player.addComponent(new ComponentPlayer());
        player.addEntityDrawer(new RenderComponentPlayer(spriteBatch));
        player.position = new Vec3(100, 200, 0);
        addEntity(player);

        // Player missile pool.
        for (int i = 0; i < 20; i++) {
            Entity missile = new Entity("player_missile", this);
            missile.addTag("player_missile");
            missile.setActive(false);
            missile.addComponent(new ComponentPlayerMissile());
            missile.addEntityDrawer(new RenderComponentPlayerMissile(spriteBatch));
            addEntity(missile);
        }

        for (int y=0;y<5;y++) {
            for (int x = 0; x < 15; x++) {
                Entity enemy = new Entity("enemy", this);
                enemy.addComponent(new ComponentEnemy());
                enemy.addEntityDrawer(new RenderComponentEnemy(spriteBatch));
                enemy.position = new Vec3(50+x*30, 50+y*30, 0);
                enemy.setActive(true);
                addEntity(enemy);
            }
        }
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
