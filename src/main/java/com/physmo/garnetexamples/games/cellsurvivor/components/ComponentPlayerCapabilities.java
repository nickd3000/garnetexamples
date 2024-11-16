package com.physmo.garnetexamples.games.cellsurvivor.components;


import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.toolkit.Component;

import java.util.HashMap;
import java.util.Map;

import static com.physmo.garnet.Utils.lerp;

public class ComponentPlayerCapabilities extends Component {

//    public static int PROJECTILE_SPEED = 1;
//    public static int PROJECTILE_RATE = 2;
//    public static int PROJECTILE_POWER = 3;
//    public static int PROJECTILE_MULTIPLIER = 4;
//    public static int PICKUP_RADIUS = 5;
//    public static int LUCK_MULTIPLIER = 6;


    double maxLevels = 10;
    Map<PlayerCapability, Integer> levels = new HashMap<PlayerCapability, Integer>();

    @Override
    public void init() {
        levels.put(PlayerCapability.PROJECTILE_SPEED, 0);
        levels.put(PlayerCapability.PROJECTILE_RATE, 0);
        levels.put(PlayerCapability.PROJECTILE_POWER, 0);
        levels.put(PlayerCapability.PROJECTILE_MULTIPLIER, 0);
        levels.put(PlayerCapability.PICKUP_RADIUS, 0);
        levels.put(PlayerCapability.LUCK_MULTIPLIER, 0);
    }

    public int getLevel(PlayerCapability levelId) {
        return levels.get(levelId);
    }

    public void increaseLevel(PlayerCapability pc) {
        levels.put(pc, levels.get(pc) + 1);
    }

    public double scaleLevel(PlayerCapability pc) {
        return Math.min(levels.get(pc), maxLevels) / maxLevels;
    }

    public double getProjectileSpeedAdjuster() {

        return lerp(1.0, 3.0, scaleLevel(PlayerCapability.PROJECTILE_SPEED));
    }

    public double getProjectileRateAdjuster() {
        return lerp(1.0, 0.3, scaleLevel(PlayerCapability.PROJECTILE_RATE));
    }

    public double getProjectilePowerAdjuster() {
        return lerp(1.0, 3.0, scaleLevel(PlayerCapability.PROJECTILE_POWER));
    }

    public int getProjectileMultiplier() {
        return (int) lerp(1.0, 5.0, scaleLevel(PlayerCapability.PROJECTILE_MULTIPLIER));
    }

    public int getPickupRadiusMultiplier() {
        return (int) lerp(1.0, 5.0, scaleLevel(PlayerCapability.PICKUP_RADIUS));
    }

    public int getLuckMultiplier() {
        return (int) lerp(1.0, 2.0, scaleLevel(PlayerCapability.LUCK_MULTIPLIER));
    }


    @Override
    public void tick(double t) {

    }

    @Override
    public void draw(Graphics g) {

    }

}
