package com.physmo.garnetexamples.games.cellsurvivor;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.toolkit.scene.SceneManager;

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
        SceneManager.addScene(new SceneGame("game"));
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
