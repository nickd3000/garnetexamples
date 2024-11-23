package com.physmo.garnetexamples.games.cellsurvivor;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.toolkit.scene.SceneManager;
import com.physmo.garnetexamples.games.cellsurvivor.scenes.SceneGame;
import com.physmo.garnetexamples.games.cellsurvivor.scenes.SceneLevelUp;
import com.physmo.garnetexamples.games.cellsurvivor.scenes.ScenePause;

public class CellSurvivor extends GarnetApp {

    public CellSurvivor(Garnet garnet, String name) {
        super(garnet, name);
    }

    public static void main(String[] args) {

        // 640 480
        // 384, 216

        Garnet garnet = new Garnet(384 * 2, 216 * 2);
        garnet.setApp(new CellSurvivor(garnet, ""));
        garnet.init();
        garnet.run();
    }

    @Override
    public void init(Garnet garnet) {
        Resources resources = new Resources();
        resources.init(garnet.getGraphics());
        SceneManager.getSharedContext().add(resources);

        SceneManager.getSharedContext().add(garnet);
        SceneManager.addScene(new SceneGame("game"));
        SceneManager.addScene(new ScenePause("pause"));
        SceneManager.addScene(new SceneLevelUp("levelUp"));
        SceneManager.setActiveScene("game");


    }

    @Override
    public void tick(double delta) {
        SceneManager.tick(delta);
    }

    @Override
    public void draw(Graphics g) {
        SceneManager.draw(g);
        g.drawRect(10, 10, 100, 100);
    }
}
