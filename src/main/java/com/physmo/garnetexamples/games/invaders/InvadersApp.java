package com.physmo.garnetexamples.games.invaders;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnetexamples.games.invaders.scenes.SceneGame;
import com.physmo.garnetexamples.games.invaders.scenes.SceneMenu;
import com.physmo.garnetexamples.games.invaders.scenes.SubScenePause;
import com.physmo.garnettoolkit.SceneManager;

// NOTE: on MacOS we need to add a vm argument: -XstartOnFirstThread
public class InvadersApp extends GarnetApp {

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
        SceneManager.getSharedContext().add(garnet);
        SceneManager.getSharedContext().add(new GameData());
        SceneManager.getSharedContext().add(new Resources().init());

        SceneManager.addScene(new SceneGame("game"));
        SceneManager.addScene(new SceneMenu("menu"));
        SceneManager.addScene(new SubScenePause("pause"));

        SceneManager.setActiveScene("menu");
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
