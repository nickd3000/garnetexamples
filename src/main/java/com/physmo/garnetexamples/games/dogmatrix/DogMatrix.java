package com.physmo.garnetexamples.games.dogmatrix;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.toolkit.scene.Scene;
import com.physmo.garnet.toolkit.scene.SceneManager;

public class DogMatrix extends GarnetApp {

    public DogMatrix(Garnet garnet, String name) {
        super(garnet, name);
    }

    public static void main(String[] args) {
        // 384x216 scales to full screen well.
        Garnet garnet = new Garnet(384, 216);
        garnet.setApp(new DogMatrix(garnet, "Dog Matrix"));

        garnet.init();
        garnet.run();
    }

    @Override
    public void init(Garnet garnet) {
        garnet.getDisplay().setWindowScale(3, true);
        garnet.getDisplay().setWindowTitle("Dog Matrix");

//        garnet.getDebugDrawer().setVisible(true);
//        garnet.getDebugDrawer().setDrawFps(true);

        SceneManager.getSharedContext().add(garnet);
        SceneManager.getSharedContext().add(new GameData());
        SceneManager.getSharedContext().add(new Resources(garnet).init());


        Scene scene = new SceneGame("game");
        SceneManager.addScene(scene);
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
