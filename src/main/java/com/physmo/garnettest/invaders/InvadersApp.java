package com.physmo.garnettest.invaders;

import com.physmo.garnet.Garnet;
import com.physmo.garnettest.invaders.states.StateGame;
import com.physmo.garnettest.invaders.states.StateMenu;
import com.physmo.garnettest.invaders.states.StatePause;

public class InvadersApp {

    public static void main(String[] args) {
        Garnet garnet = new Garnet(640, 480);
        garnet.init();
        garnet.addGlobalObject("game_data", new GameData());
        garnet.addGlobalObject("Resources", new Resources().init());

        garnet.addState(new StateMenu(garnet, "menu"));
        garnet.addState(new StateGame(garnet, "game"));
        garnet.addState(new StatePause(garnet, "pause"));

        garnet.switchActiveState("menu");

        garnet.run();
    }

}
