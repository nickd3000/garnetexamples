package com.physmo.garnettest.invaders.components;


import com.physmo.garnet.color.Color;
import com.physmo.garnet.color.ColorSupplierLinear;
import com.physmo.garnet.curve.CurveType;
import com.physmo.garnet.curve.StandardCurve;
import com.physmo.garnet.particle.ParticleTemplate;
import com.physmo.garnet.spritebatch.Sprite2D;
import com.physmo.garnet.spritebatch.SpriteBatch;
import com.physmo.garnettest.invaders.Constants;
import com.physmo.garnettest.invaders.EnemyType;
import com.physmo.garnettest.invaders.GameData;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.Rect;
import com.physmo.garnettoolkit.SceneManager;
import com.physmo.garnettoolkit.simplecollision.Collidable;
import com.physmo.garnettoolkit.simplecollision.CollisionPacket;
import com.physmo.garnettoolkit.simplecollision.CollisionSystem;

import java.util.List;

public class ComponentEnemy extends Component implements Collidable {

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

        CollisionSystem collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);
        collisionSystem.addCollidable(this);

        gameData = SceneManager.getSharedContext().getObjectByType(GameData.class);

        resetFireDelay();
        health = 1;

        explosionParticleTemplate = new ParticleTemplate();
        explosionParticleTemplate.setLifeTime(0.2, 3);
        explosionParticleTemplate.setSpeed(10, 50);
        explosionParticleTemplate.setPositionJitter(2.1);
        explosionParticleTemplate.setColorSupplier(new ColorSupplierLinear(Color.YELLOW, new Color(1, 0, 0, 0)));
        explosionParticleTemplate.setSpeedCurve(new StandardCurve(CurveType.LINE_DOWN));

        parent.addTag("enemy");
    }

    public void resetFireDelay() {
        fireDelay = 2 + Math.random() * 4;
    }


    @Override
    public void tick(double delta) {

        ComponentGameLogic gameLogic = parent.getContext().getComponent(ComponentGameLogic.class);

        if (gameLogic != null) {
            if (gameLogic.dir) {
                parent.getTransform().x += 10 * delta;
            } else {
                parent.getTransform().x -= 10 * delta;
            }
        }

        fireDelay -= delta;
        if (fireDelay < 0) {
            if (enemyType == EnemyType.shooter) fireMissile();
            resetFireDelay();
        }
    }

    private void fireMissile() {

        List<GameObject> missiles = parent.getContext().getObjectsByTag(Constants.ENEMY_MISSILE);
        for (GameObject missile : missiles) {
            if (!missile.isActive()) {
                missile.setActive(true);
                missile.setVisible(true);
                missile.getTransform().x = parent.getTransform().x;
                missile.getTransform().y = parent.getTransform().y;
                break;
            }
        }
    }

    @Override
    public void draw() {
        if (!parent.isActive()) return;

        Sprite2D spr = Sprite2D.build(
                (int) (parent.getTransform().x) - 8,
                (int) (parent.getTransform().y) - 8,
                16, 16, 32, 32, 16, 16);

        ComponentEnemy component = parent.getComponent(ComponentEnemy.class);

        if (component.enemyType == EnemyType.basic) spr.addColor(0, 1, 0);
        if (component.enemyType == EnemyType.armoured) spr.addColor(0.5f, 0.6f, 0.7f);
        if (component.enemyType == EnemyType.shooter) spr.addColor(1, 0.5f, 0);
        spriteBatch.add(spr);
    }

    @Override
    public Rect collisionGetRegion() {
        Rect rect = new Rect();
        rect.set(parent.getTransform().x - 7, parent.getTransform().y - 7, 14, 14);
        return rect;
    }

    @Override
    public void collisionCallback(CollisionPacket collisionPacket) {
        GameObject otherObject = collisionPacket.targetEntity.collisionGetGameObject();

        if (otherObject.getTags().contains(Constants.PLAYER_MISSILE)) {
            System.out.println("missile hit");
            handleBulletHit(1);
        }
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

        //Emitter emitter = new Emitter(parent.position, 0.2, explosionParticleTemplate);
        //emitter.setEmitPerSecond(1500);
        //parentState.getParticleManager().addEmitter(emitter);
    }

    @Override
    public GameObject collisionGetGameObject() {
        return parent;
    }
}
