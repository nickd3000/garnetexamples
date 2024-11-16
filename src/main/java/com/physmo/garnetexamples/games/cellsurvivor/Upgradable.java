package com.physmo.garnetexamples.games.cellsurvivor;

public interface Upgradable {
    int getCurrentLevel();

    int getMaxLevel();

    String getLevelDescription(int level);
}
