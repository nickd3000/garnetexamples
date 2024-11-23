package com.physmo.garnetexamples.games.cellsurvivor.components.weapons;

import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.toolkit.Component;
import com.physmo.garnet.toolkit.simplecollision.Collidable;
import com.physmo.garnet.toolkit.simplecollision.ColliderComponent;
import com.physmo.garnet.toolkit.simplecollision.CollisionSystem;
import com.physmo.garnetexamples.games.cellsurvivor.Constants;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentPlayerCapabilities;
import com.physmo.garnetexamples.games.cellsurvivor.components.ParticleFactory;
import com.physmo.garnetexamples.games.cellsurvivor.components.ProjectileType;
import com.physmo.garnetexamples.games.cellsurvivor.components.SpriteHelper;

public class Bullet extends Component implements DamageSupplier {

    double speed = 50;
    ProjectileType projectileType = ProjectileType.BULLET;
    double dx = 0, dy = 0;
    boolean killMe = false;
    double age = 0;
    SpriteHelper spriteHelper;
    ColliderComponent colliderComponent;
    ComponentPlayerCapabilities playerCapabilities;
    ParticleFactory particleFactory;
    int numEnemiesHit = 0;
    int pierce = 1;
    double damage = 1;

    public void setDirection(double x, double y) {
        dx = x;
        dy = y;
    }


    @Override
    public void init() {
        playerCapabilities = parent.getContext().getComponent(ComponentPlayerCapabilities.class);
        particleFactory = parent.getContext().getComponent(ParticleFactory.class);

        spriteHelper = parent.getContext().getComponent(SpriteHelper.class);

        colliderComponent = parent.getComponent(ColliderComponent.class);

        colliderComponent.setCallbackEnter(target -> {
            if (target.hasTag(Constants.TAG_ENEMY)) {
                numEnemiesHit++;
                if (numEnemiesHit >= pierce) {
                    killMe = true;
                }

            }
        });

    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void tick(double t) {
        age += t;
        double speedAdjuster = playerCapabilities.getProjectileSpeedAdjuster();
        parent.getTransform().x += dx * t * speed * speedAdjuster;
        parent.getTransform().y += dy * t * speed * speedAdjuster;

        if (age > 3) killMe = true;

        if (killMe) {
            CollisionSystem collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);
            Collidable collidable = parent.getComponent(ColliderComponent.class);
            collisionSystem.removeCollidable(collidable);
            parent.destroy();
        }

        colliderComponent.setCollisionRegion(-2, -2, 4, 4);


        if (projectileType == ProjectileType.MAGIC && Math.random() < 0.2) {
            particleFactory.createParticle(particleFactory.wandTrail, parent.getTransform());
        }
        if (projectileType == ProjectileType.FIREBALL && Math.random() < 0.1) {
            particleFactory.createParticle(particleFactory.flame, parent.getTransform());
        }
        if (projectileType == ProjectileType.FIREBALL && Math.random() < 0.05) {
            particleFactory.createParticle(particleFactory.lightSmoke, parent.getTransform());
        }
    }

    @Override
    public void draw(Graphics g) {
        int x = (int) parent.getTransform().x;
        int y = (int) parent.getTransform().y;

        //(vector3.getAngle() / (Math.PI * 2)) * 360;
        double angle = (getAngle() / (Math.PI * 2)) * 360;

        if (projectileType == ProjectileType.BULLET) {
            spriteHelper.drawSpriteInMap(x, y, 3, 2, angle);
        } else if (projectileType == ProjectileType.MAGIC) {
            spriteHelper.drawSpriteInMap(x - 8, y - 8, 2, 2);
        } else if (projectileType == ProjectileType.FIREBALL) {
            spriteHelper.drawSpriteInMap(x, y, 5, 2, angle);
        }
    }

    public double getAngle() {
        double a = Math.atan2(-dx, -dy);
        a = a > 0 ? (Math.PI * 2) - a : 0 - a;
        return a;
    }

    public ProjectileType getProjectileType() {
        return projectileType;
    }

    public void setProjectileType(ProjectileType type) {
        this.projectileType = type;
    }

    public void setPierce(int pierce) {
        this.pierce = pierce;
    }

    @Override
    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
}
