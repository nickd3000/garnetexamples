package com.physmo.garnetexamples.games.molepanic;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.toolkit.scene.SceneManager;

public class MolePanic extends GarnetApp {

    public MolePanic(Garnet garnet, String name) {
        super(garnet, name);
    }

    public static void main(String[] args) {
        Garnet garnet = new Garnet(640, 480);
        garnet.setApp(new MolePanic(garnet, "Mole Panic"));

        garnet.init();
        garnet.run();
    }

    @Override
    public void init(Garnet garnet) {
        System.out.println("main init");
        SceneManager.getSharedContext().add(garnet);
        SceneManager.getSharedContext().add(new GameData());
        SceneManager.getSharedContext().add(new Resources(garnet).init());

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
    }
}
