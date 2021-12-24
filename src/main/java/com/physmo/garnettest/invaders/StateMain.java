package com.physmo.garnettest.invaders;

import com.physmo.garnet.GameState;
import com.physmo.garnet.Garnet;
import com.physmo.garnet.Texture;
import com.physmo.garnet.Vec3;
import com.physmo.garnet.entity.Entity;
import com.physmo.garnet.spritebatch.SpriteBatch;

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
    }

    @Override
    public void tick(double delta) {
    }

    @Override
    public void draw() {
        spriteBatch.render();
        spriteBatch.clear();
    }
}
