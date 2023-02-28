package com.physmo.garnettest.invaders;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnettest.invaders.states.StateGame;
import com.physmo.garnettoolkit.SceneManager;

public class InvadersApp extends GarnetApp {

    SceneManager sceneManager;

    public InvadersApp(Garnet garnet, String name) {
        super(garnet, name);
    }

    public static void main(String[] args) {
        Garnet garnet = new Garnet(640, 480);
        garnet.setApp(new InvadersApp(garnet, ""));

        garnet.init();


        garnet.run();
    }

    @Override
    public void init(Garnet garnet) {
        sceneManager = new SceneManager();

        sceneManager.getSharedContext().add(garnet);
        sceneManager.getSharedContext().add(new GameData());
        sceneManager.getSharedContext().add(new Resources().init());

//        garnet.addSharedObject("game_data", new GameData());
//        garnet.addSharedObject("Resources", new Resources().init());

        sceneManager.addScene(new StateGame("game", garnet));

        //garnet.addState(new StateGame(garnet, "game"));
        //garnet.addState(new StateMenu(garnet, "menu"));
        //garnet.addState(new StatePause(garnet, "pause"));

        //garnet.switchActiveState("menu");
        sceneManager.setActiveScene("game");
    }

    @Override
    public void tick(double delta) {
        sceneManager.tick(delta);
    }

    @Override
    public void draw() {
        sceneManager.draw();
    }
}
