package com.physmo.garnetexamples.games.molepanic;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.toolkit.scene.Scene;
import com.physmo.garnet.toolkit.scene.SceneManager;
import com.physmo.garnetexamples.games.molepanic.components.LevelLogic;


public class SceneGame extends Scene {

    public SceneGame(String name) {
        super(name);
    }

    @Override
    public void init() {
        context.reset();
        LevelLogic levelLogic = new LevelLogic("");
        context.add(levelLogic);
        Garnet garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        garnet.getGraphics().setZoom(2);
    }

    @Override
    public void tick(double delta) {

    }

    @Override
    public void draw(Graphics g) {

    }

    @Override
    public void onMakeActive() {

    }

    @Override
    public void onMakeInactive() {

    }
}
