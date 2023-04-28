package com.physmo.garnetexamples;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;

public class HelloWorld extends GarnetApp {

    public HelloWorld(Garnet garnet, String name) {
        super(garnet, name);
    }

    public static void main(String[] args) {
        Garnet garnet = new Garnet(400, 400);
        GarnetApp app = new HelloWorld(garnet, "");

        garnet.setApp(app);

        garnet.init();
        garnet.run();
    }

    @Override
    public void init(Garnet garnet) {
        System.out.println("Hello, World");
    }

    @Override
    public void tick(double delta) {

    }

    @Override
    public void draw() {
        garnet.getGraphics().drawCircle(200, 200, 100, 100);
        garnet.getGraphics().render();
    }
}
