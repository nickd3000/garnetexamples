package com.physmo.garnettest;

import com.physmo.garnet.Garnet;
import com.physmo.garnettest.basicsprites.ContainerBasicSprites;
//import old.TestGameContainer;

// NOTE: on MacOS we need to add a vm argument: -XstartOnFirstThread
public class TestGarnet {
    public static void main(String[] args) {
        Garnet garnet = new Garnet(new ContainerBasicSprites(), 640, 480);
        //Garnet garnet = new Garnet(new ContainerRegularFontSample(),640,480);
        //Garnet garnet = new Garnet(new Boids(), 640, 480);
        garnet.init();
        garnet.run();
    }
}
