package com.physmo.garnettest.invaders;

import com.physmo.garnet.Garnet;

public class InvadersApp {

    public static void main(String[] args) {
        Garnet garnet = new Garnet(640, 480);
        garnet.init();
        garnet.addGlobalObject("game_data", new GameData());
        garnet.addGlobalObject("Resources", new Resources().init());

        garnet.addState(new StateMenu(garnet, "menu"));
        garnet.addState(new StateMain(garnet, "main"));
        garnet.addState(new StatePause(garnet, "pause"));

        garnet.switchActiveState("menu");

        garnet.run();
    }

}
