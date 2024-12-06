package com.physmo.garnetexamples.games.cellsurvivor.components.weapons;

import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.toolkit.Component;
import com.physmo.garnet.toolkit.scene.SceneManager;
import com.physmo.garnet.toolkit.simplecollision.CollisionSystem;
import com.physmo.garnetexamples.games.cellsurvivor.EntityFactory;
import com.physmo.garnetexamples.games.cellsurvivor.Resources;
import com.physmo.garnetexamples.games.cellsurvivor.Upgradable;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentPlayerCapabilities;
import com.physmo.garnetexamples.games.cellsurvivor.components.ProjectileType;
import com.physmo.garnetexamples.games.cellsurvivor.components.items.CombinedItemStats;
import com.physmo.garnetexamples.games.cellsurvivor.gamedata.GDWeapon;

public class GlaveGun extends Component implements Weapon, Upgradable {
    double cooldownPeriod = 8.0;
    double cooldown = 0.1;

    CollisionSystem collisionSystem;
    ComponentPlayerCapabilities playerCapabilities;

    int level = 0;
    WeaponStats weaponStats = new WeaponStats();
    Resources resources;
    GDWeapon gdWeapon;
    int maxLevel = 15;
    CombinedItemStats combinedItemStats;
    @Override
    public void init() {

        collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);

        playerCapabilities = parent.getContext().getComponent(ComponentPlayerCapabilities.class);

        resources = SceneManager.getSharedContext().getObjectByType(Resources.class);

        gdWeapon = resources.getGameData().getWeaponByName("glave");
        combinedItemStats = parent.getComponent(CombinedItemStats.class);

        weaponStats.refreshStats(gdWeapon, level, combinedItemStats);
    }

    public double getEffectiveCooldownPeriod() {
        return cooldownPeriod * playerCapabilities.getProjectileRateAdjuster();
    }

    @Override
    public void tick(double t) {
        cooldown -= t;
        if (cooldown < 0) {
            cooldown += weaponStats.get(WeaponStatType.COOLDOWN).value;
            int shotCount = (int) weaponStats.get(WeaponStatType.COUNT).value;
            for (int i = 0; i < shotCount; i++) {
                fire(i, shotCount);
            }
        }

        weaponStats.refreshStatsOnTimeout(t, gdWeapon, level, combinedItemStats);
    }

    public void fire(int bulletNumber, int bulletTotal) {
        double lifeTime = weaponStats.get(WeaponStatType.DURATION).value;
        double radius = 40;
        double speed = weaponStats.get(WeaponStatType.SPEED).value;
        double damage = weaponStats.get(WeaponStatType.DAMAGE).value;
        EntityFactory.createOrbitingBullet(parent.getContext(), collisionSystem, parent, radius, speed, bulletNumber, bulletTotal, ProjectileType.GLAVE, lifeTime, damage);
    }


    @Override
    public void draw(Graphics g) {

    }

    @Override
    public String getName() {
        return "Glave Gun";
    }


    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void increaseLevel() {
        level++;
        weaponStats.refreshStats(gdWeapon, level, combinedItemStats);
    }

    @Override
    public String getLevelDescription(int level) {
        return "";
    }
}
