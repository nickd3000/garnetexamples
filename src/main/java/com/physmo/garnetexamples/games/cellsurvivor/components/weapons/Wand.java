package com.physmo.garnetexamples.games.cellsurvivor.components.weapons;

import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.structure.Array;
import com.physmo.garnet.toolkit.Component;
import com.physmo.garnet.toolkit.scene.SceneManager;
import com.physmo.garnet.toolkit.simplecollision.CollisionSystem;
import com.physmo.garnet.toolkit.simplecollision.RelativeObject;
import com.physmo.garnetexamples.games.cellsurvivor.EntityFactory;
import com.physmo.garnetexamples.games.cellsurvivor.Resources;
import com.physmo.garnetexamples.games.cellsurvivor.Upgradable;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentPlayer;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentPlayerCapabilities;
import com.physmo.garnetexamples.games.cellsurvivor.components.ProjectileType;
import com.physmo.garnetexamples.games.cellsurvivor.components.items.CombinedItemStats;
import com.physmo.garnetexamples.games.cellsurvivor.gamedata.GDWeapon;

import java.util.Comparator;
import java.util.List;

public class Wand extends Component implements Weapon, Upgradable {

    double cooldownPeriod = 0.7;
    double cooldown = cooldownPeriod;

    CollisionSystem collisionSystem;
    ComponentPlayerCapabilities playerCapabilities;

    int level = 20;
    WeaponStats weaponStats = new WeaponStats();
    Resources resources;
    GDWeapon gdWeapon;
    double subShotTimer;
    int pendingShots = 0;

    @Override
    public void init() {

        collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);

        playerCapabilities = parent.getContext().getComponent(ComponentPlayerCapabilities.class);
        resources = SceneManager.getSharedContext().getObjectByType(Resources.class);

        gdWeapon = resources.getGameData().getWeaponByName("wand");
        CombinedItemStats combinedItemStats = parent.getComponent(CombinedItemStats.class);
        weaponStats.refreshStats(gdWeapon, level, combinedItemStats);
    }

    @Override
    public void tick(double t) {
        cooldown -= t;
        if (cooldown < 0) {
            //cooldown += cooldownPeriod * playerCapabilities.getProjectileRateAdjuster();
            cooldown += weaponStats.get(WeaponStatType.COOLDOWN).value;
            pendingShots += (int) weaponStats.get(WeaponStatType.COUNT).value;
        }

        subShotTimer -= t;
        if (subShotTimer < 0) {
            subShotTimer += weaponStats.get(WeaponStatType.INTERVAL).value;
            if (pendingShots > 0) {

                fire(pendingShots);
                pendingShots = 0;
            }
        }

    }

    public void fire(int count) {
        List<RelativeObject> nearestObjects = parent.getComponent(ComponentPlayer.class).getNearestEnemies();
        if (nearestObjects.isEmpty()) return;

        // Copy list to array
        Array<RelativeObject> nos = new Array<>(10);
        nearestObjects.forEach(nos::add);

        // sort array
        nos.sort(Comparator.comparingDouble(RelativeObject::getDistance));

        for (int i = 0; i < count; i++) {
            RelativeObject relativeObject = nos.get(i % nos.size());

            createBullet(parent.getTransform().x, parent.getTransform().y, relativeObject.dx, relativeObject.dy);
        }
    }

    public void createBullet(double x, double y, double dx, double dy) {
        double bulletSpeed = weaponStats.get(WeaponStatType.SPEED).value;
        int pierce = (int) weaponStats.get(WeaponStatType.PIERCE).value;
        double damage = weaponStats.get(WeaponStatType.DAMAGE).value;

        EntityFactory.createSimpleBullet(parent.getContext(), collisionSystem, x, y, dx, dy, bulletSpeed, ProjectileType.MAGIC, pierce, damage);
    }

    @Override
    public void draw(Graphics g) {

    }

    @Override
    public String getName() {
        return "Magic Wand";
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void increaseLevel() {
        level++;
    }

    @Override
    public int getCurrentLevel() {
        return 0;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public String getLevelDescription(int level) {
        return "";
    }
}
