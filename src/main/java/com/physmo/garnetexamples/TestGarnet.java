package com.physmo.garnetexamples;

import com.physmo.garnet.Garnet;

// NOTE: on MacOS we need to add a vm argument: -XstartOnFirstThread
public class TestGarnet {
    public static void main(String[] args) {
        //Garnet garnet = new Garnet(new ContainerBasicSprites(), 640, 480);
        //Garnet garnet = new Garnet(new ContainerRegularFontSample(),640,480);
        Garnet garnet = new Garnet(640, 480);
        //garnet.addState( new Boids(garnet, "boids"));
        garnet.init();
        garnet.run();
    }
}