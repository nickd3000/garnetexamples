package com.physmo.garnetexamples.cellsurvivor;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnettoolkit.SceneManager;

public class CellSurvivor extends GarnetApp {

    public CellSurvivor(Garnet garnet, String name) {
        super(garnet, name);
    }

    public static void main(String[] args) {
        Garnet garnet = new Garnet(640, 480);
        garnet.setApp(new CellSurvivor(garnet, ""));

        garnet.init();
        garnet.run();
    }

    @Override
    public void init(Garnet garnet) {
        SceneManager.getSharedContext().add(garnet);
//        SceneManager.getSharedContext().add(new GameData());
//        SceneManager.getSharedContext().add(new Resources().init());
//
//        SceneManager.addScene(new SceneGame("game"));
//        SceneManager.addScene(new SceneMenu("menu"));
//        SceneManager.addScene(new SubScenePause("pause"));
//
//        SceneManager.setActiveScene("menu");

        SceneManager.addScene(new SceneGame("game"));
        SceneManager.setActiveScene("game");
    }

    @Override
    public void tick(double delta) {
        SceneManager.tick(delta);
    }

    @Override
    public void draw() {
        SceneManager.draw();
        garnet.getGraphics().render();
    }
}
