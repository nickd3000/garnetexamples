package com.physmo.garnetexamples.games.cellsurvivor.components.weapons;

import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.toolkit.Component;
import com.physmo.garnet.toolkit.simplecollision.CollisionSystem;
import com.physmo.garnet.toolkit.simplecollision.RelativeObject;
import com.physmo.garnetexamples.games.cellsurvivor.EntityFactory;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentPlayer;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentPlayerCapabilities;
import com.physmo.garnetexamples.games.cellsurvivor.components.ProjectileType;

import java.util.List;
import java.util.Random;

public class Bow extends Component implements Weapon {

    double cooldownPeriod = 1.0;
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

    @Override
    public void tick(double t) {
        cooldown -= t;
        if (cooldown < 0) {
            cooldown += cooldownPeriod * playerCapabilities.getProjectileRateAdjuster();
            int mult = playerCapabilities.getProjectileMultiplier();
            for (int i = 0; i < mult; i++) {
                fire();
            }
        }
    }

    public void fire() {
        List<RelativeObject> nearestObjects = parent.getComponent(ComponentPlayer.class).getNearestEnemies();
        if (nearestObjects.size() < 1) return;
        RelativeObject relativeObject = nearestObjects.get(random.nextInt(nearestObjects.size()));
        createBullet(parent.getTransform().x, parent.getTransform().y, relativeObject.dx, relativeObject.dy);
    }

    public void createBullet(double x, double y, double dx, double dy) {
        EntityFactory.createSimpleBullet(parent.getContext(), collisionSystem, x, y, dx, dy, projectileSpeed, ProjectileType.BULLET);
    }

    @Override
    public void draw(Graphics g) {

    }

    @Override
    public String getName() {
        return "Short Bow";
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
