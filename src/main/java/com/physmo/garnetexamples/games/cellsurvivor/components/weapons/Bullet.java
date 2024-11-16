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

public class Bullet extends Component {

    double speed = 50;
    ProjectileType projectileType = ProjectileType.BULLET;
    double dx = 0, dy = 0;
    boolean killMe = false;
    double age = 0;
    SpriteHelper spriteHelper;
    ColliderComponent colliderComponent;
    ComponentPlayerCapabilities playerCapabilities;
    ParticleFactory particleFactory;

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
                killMe = true;
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

        // Handle particles.
//        if (projectileType == ProjectileType.MAGIC && Math.random()<0.3) {
//            Emitter emitter = new Emitter(parent.getTransform(), 0.2, wandParticleTemplate);
//            emitter.setEmitPerSecond(150);
//            particleManager.addEmitter(emitter);
//        }

        if (projectileType == ProjectileType.MAGIC && Math.random() < 0.3) {
//            Particle freeParticle = particleManager.getFreeParticle();
//            if (freeParticle != null) {
//                wandParticleTemplate.initParticle(freeParticle, parent.getTransform());
//            }
            particleFactory.createParticle(particleFactory.wandTrail, parent.getTransform());
        }
    }

    @Override
    public void draw(Graphics g) {
        int x = (int) parent.getTransform().x;
        int y = (int) parent.getTransform().y;

        if (projectileType == ProjectileType.BULLET) {
            spriteHelper.drawSpriteInMap(x - 8, y - 8, 0, 2);
        } else if (projectileType == ProjectileType.MAGIC) {
            spriteHelper.drawSpriteInMap(x - 8, y - 8, 2, 2);
        }
    }

    public ProjectileType getProjectileType() {
        return projectileType;
    }

    public void setProjectileType(ProjectileType type) {
        this.projectileType = type;
    }
}
