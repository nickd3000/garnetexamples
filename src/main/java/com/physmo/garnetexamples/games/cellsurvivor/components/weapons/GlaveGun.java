package com.physmo.garnetexamples.games.cellsurvivor.components.weapons;

import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.toolkit.Component;
import com.physmo.garnet.toolkit.simplecollision.CollisionSystem;
import com.physmo.garnetexamples.games.cellsurvivor.EntityFactory;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentPlayerCapabilities;
import com.physmo.garnetexamples.games.cellsurvivor.components.ProjectileType;

import java.util.Random;

public class GlaveGun extends Component implements Weapon {
    double cooldownPeriod = 8.0;
    double cooldown = cooldownPeriod;
    Random random = new Random();
    CollisionSystem collisionSystem;
    ComponentPlayerCapabilities playerCapabilities;
    double projectileSpeed = 70;
    int level = 0;

    @Override
    public void init() {
        collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);

        playerCapabilities = parent.getContext().getComponent(ComponentPlayerCapabilities.class);

    }

    public double getEffectiveCooldownPeriod() {
        return cooldownPeriod * playerCapabilities.getProjectileRateAdjuster();
    }

    @Override
    public void tick(double t) {
        cooldown -= t;
        if (cooldown < 0) {
            cooldown += getEffectiveCooldownPeriod();
            int shotCount = 3 * playerCapabilities.getProjectileMultiplier();
            for (int i = 0; i < shotCount; i++) {
                fire(i, shotCount);
            }
        }
    }

    public void fire(int bulletNumber, int bulletTotal) {
//        createBullet(parent.getTransform().x, parent.getTransform().y, relativeObject.dx, relativeObject.dy);
        System.out.println("create orbiter");
        double lifeTime = getEffectiveCooldownPeriod() * 0.8;
        double radius = 40;
        double speed = 3;
        EntityFactory.createOrbitingBullet(parent.getContext(), collisionSystem, parent, radius, speed, bulletNumber, bulletTotal, ProjectileType.GLAVE, lifeTime);
    }


    @Override
    public void draw(Graphics g) {

    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void increaseLevel() {
        level++;
    }
}
