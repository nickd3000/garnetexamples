package com.physmo.garnetexamples.games.cellsurvivor.components;


public enum PlayerCapability {

    PROJECTILE_SPEED("Projectile Speed"),
    PROJECTILE_RATE("Projectile Rate"),
    PROJECTILE_POWER("Projectile Power"),
    PROJECTILE_MULTIPLIER("Projectile Multiplier"),
    PICKUP_RADIUS("Pickup Radius"),
    LUCK_MULTIPLIER("Luck Multiplier");

    final String name;

    PlayerCapability(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
