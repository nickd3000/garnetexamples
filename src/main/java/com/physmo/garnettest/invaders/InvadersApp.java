package com.physmo.garnettest.invaders;

import com.physmo.garnet.Garnet;

public class InvadersApp {

    public static void main(String[] args) {
        Garnet garnet = new Garnet(new StateMain(), 640, 480);
        garnet.init();
        garnet.run();
    }

}
