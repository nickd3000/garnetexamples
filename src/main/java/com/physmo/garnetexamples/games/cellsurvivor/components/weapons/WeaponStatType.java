package com.physmo.garnetexamples.games.cellsurvivor.components.weapons;

public enum WeaponStatType {
    DAMAGE(false),
    SPEED(false),
    COOLDOWN(false),
    COUNT(true),
    PIERCE(true),
    INTERVAL(false),
    KNOCK_BACK(false),
    DURATION(false);

    final boolean isInteger;

    WeaponStatType(boolean isInteger) {
        this.isInteger = isInteger;
    }

    public boolean isInteger() {
        return isInteger;
    }
}
