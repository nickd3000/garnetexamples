package com.physmo.garnetexamples.games.cellsurvivor;

public interface Upgradable {

    int getMaxLevel();

    int getLevel();

    void increaseLevel();

    String getLevelDescription(int level);

    String getName();
}
