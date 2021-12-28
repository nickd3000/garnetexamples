package com.physmo.garnettest.invaders;

import com.physmo.garnet.Garnet;

public class InvadersApp {

    public static void main(String[] args) {
        Garnet garnet = new Garnet( 640, 480);
        garnet.addState("menu", new StateMenu());
        garnet.addState("main", new StateMain());
        garnet.init();
        garnet.run();
    }

}
