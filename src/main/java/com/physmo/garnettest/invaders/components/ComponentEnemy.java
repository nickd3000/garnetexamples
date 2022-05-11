package com.physmo.garnettest.invaders.components;

import com.physmo.garnet.collision.BoxCollider2D;
import com.physmo.garnet.collision.CollisionPacket;
import com.physmo.garnet.color.Color;
import com.physmo.garnet.color.ColorSupplierLinear;
import com.physmo.garnet.curve.CurveType;
import com.physmo.garnet.curve.StandardCurve;
import com.physmo.garnet.entity.Component;
import com.physmo.garnet.entity.Entity;
import com.physmo.garnet.particle.Emitter;
import com.physmo.garnet.particle.ParticleTemplate;
import com.physmo.garnet.spritebatch.Sprite2D;
import com.physmo.garnet.spritebatch.SpriteBatch;
import com.physmo.garnettest.invaders.EnemyType;
import com.physmo.garnettest.invaders.GameData;

import java.util.List;

public class ComponentEnemy extends Component {

    public EnemyType enemyType;
    GameData gameData;
    double fireDelay;
    double health;
    ParticleTemplate explosionParticleTemplate;
    SpriteBatch spriteBatch;

    public ComponentEnemy(EnemyType enemyType, SpriteBatch spriteBatch) {
        this.enemyType = enemyType;
        this.spriteBatch = spriteBatch;
    }

    @Override
    public void init() {
        BoxCollider2D boxCollider2D = new BoxCollider2D();
        boxCollider2D.setValues(parent, -7, -7, 14, 14);
        parent.addCollider(boxCollider2D);

        gameData = parent.garnet.getSharedObject(GameData.class);

        resetFireDelay();
        health = 1;

        explosionParticleTemplate = new ParticleTemplate();
        explosionParticleTemplate.setLifeTime(0.2, 3);
        explosionParticleTemplate.setSpeed(10, 50);
        explosionParticleTemplate.setPositionJitter(2.1);
        explosionParticleTemplate.setColorSupplier(new ColorSupplierLinear(Color.YELLOW, new Color(1, 0, 0, 0)));
        explosionParticleTemplate.setSpeedCurve(new StandardCurve(CurveType.LINE_DOWN));
    }

    public void resetFireDelay() {
        fireDelay = 2 + Math.random() * 4;
    }


    @Override
    public void tick(double delta) {

        ComponentGameLogic gameLogic = getComponent(ComponentGameLogic.class);

        if (gameLogic != null) {
            if (gameLogic.dir) {
                parent.position.x += 10 * delta;
            } else {
                parent.position.x -= 10 * delta;
            }
        }

        fireDelay -= delta;
        if (fireDelay < 0) {
            if (enemyType == EnemyType.shooter) fireMissile();
            resetFireDelay();
        }
    }

    private void fireMissile() {
        List<Entity> missiles = parent.getGameState().getEntitiesByTag("enemy_missile");
        for (Entity missile : missiles) {
            if (!missile.getActive()) {
                missile.setActive(true);
                missile.setVisible(true);
                missile.position.x = parent.position.x;
                missile.position.y = parent.position.y;
                break;
            }
        }
    }

    @Override
    public void onCollisionStart(CollisionPacket collisionPacket) {
        if (!collisionPacket.sourceEntity.hasTag("player_missile")) return;
        handleBulletHit(1);

        // todo: spawn particle on bullet hit (not the same as the explosion)
    }

    private void handleBulletHit(double bulletDamage) {
        double damageScale = 1;

        if (enemyType == EnemyType.armoured) damageScale = 1.0f / 3.0f;

        health -= bulletDamage * damageScale;
        if (health <= 0) handleDying();
    }

    private void handleDying() {
        parent.setActive(false);
        gameData.currentScore++;

        Emitter emitter = new Emitter(parent.position, 0.2, explosionParticleTemplate);
        emitter.setEmitPerSecond(1500);
        parentState.getParticleManager().addEmitter(emitter);
    }

    @Override
    public void draw() {
        Sprite2D spr = Sprite2D.build(
                (int) (parent.position.x) - 8,
                (int) (parent.position.y) - 8,
                16, 16, 32, 32, 16, 16);

        ComponentEnemy component = parent.getComponent(ComponentEnemy.class);

        if (component.enemyType == EnemyType.basic) spr.addColor(0, 1, 0);
        if (component.enemyType == EnemyType.armoured) spr.addColor(0.5f, 0.6f, 0.7f);
        if (component.enemyType == EnemyType.shooter) spr.addColor(1, 0.5f, 0);
        spriteBatch.add(spr);
    }
}
