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

public class FireWand extends Component implements Weapon, Upgradable {

    double cooldownPeriod = 0.7;
    double cooldown = cooldownPeriod;

    CollisionSystem collisionSystem;
    ComponentPlayerCapabilities playerCapabilities;
    int level = 0;
    WeaponStats weaponStats = new WeaponStats();
    Resources resources;
    GDWeapon gdWeapon;
    int maxLevel = 15;
    CombinedItemStats combinedItemStats;
    double statsRefreshTimer = 1;

    @Override
    public void init() {

        collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);

        playerCapabilities = parent.getContext().getComponent(ComponentPlayerCapabilities.class);
        resources = SceneManager.getSharedContext().getObjectByType(Resources.class);

        gdWeapon = resources.getGameData().getWeaponByName("fire_wand");
        combinedItemStats = parent.getComponent(CombinedItemStats.class);
        weaponStats.refreshStats(gdWeapon, level, combinedItemStats);
    }

    @Override
    public void tick(double t) {
        cooldown -= t;
        if (cooldown < 0) {
            //cooldown += cooldownPeriod * playerCapabilities.getProjectileRateAdjuster();
            cooldown += weaponStats.get(WeaponStatType.COOLDOWN).value;
            int count = (int) weaponStats.get(WeaponStatType.COUNT).value;
            fire(count);
        }

        weaponStats.refreshStatsOnTimeout(t, gdWeapon, level, combinedItemStats);

    }

    public void fire(int count) {
        double angle = Math.random() * Math.PI * 2;
        double spread = Math.PI / 20.0;
        for (int i = 0; i < count; i++) {
            double dx = Math.sin(angle + (i * spread));
            double dy = Math.cos(angle + (i * spread));
            createBullet(parent.getTransform().x, parent.getTransform().y, dx, dy);
        }
    }

    public void createBullet(double x, double y, double dx, double dy) {
        double bulletSpeed = weaponStats.get(WeaponStatType.SPEED).value;
        int pierce = (int) weaponStats.get(WeaponStatType.PIERCE).value;
        double damage = weaponStats.get(WeaponStatType.DAMAGE).value;

        EntityFactory.createSimpleBullet(parent.getContext(), collisionSystem, x, y, dx, dy, bulletSpeed, ProjectileType.FIREBALL, pierce, damage);
    }

    @Override
    public void draw(Graphics g) {

    }

    @Override
    public String getName() {
        return "Fire Wand";
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
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public String getLevelDescription(int level) {
        return "";
    }
}
