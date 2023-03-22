package com.physmo.garnetexamples.invaders.components;


import com.physmo.garnet.Garnet;
import com.physmo.garnet.Utils;
import com.physmo.garnet.spritebatch.TileSheet;
import com.physmo.garnetexamples.invaders.Constants;
import com.physmo.garnetexamples.invaders.EnemyType;
import com.physmo.garnetexamples.invaders.GameData;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.Rect;
import com.physmo.garnettoolkit.SceneManager;
import com.physmo.garnettoolkit.color.Color;
import com.physmo.garnettoolkit.color.ColorSupplierLinear;
import com.physmo.garnettoolkit.curve.CurveType;
import com.physmo.garnettoolkit.curve.StandardCurve;
import com.physmo.garnettoolkit.particle.Emitter;
import com.physmo.garnettoolkit.particle.ParticleManager;
import com.physmo.garnettoolkit.particle.ParticleTemplate;
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

    TileSheet tileSheet;
    Garnet garnet;

    public ComponentEnemy(EnemyType enemyType) {
        this.enemyType = enemyType;
    }
    int basicCol = Utils.floatToRgb(0, 1, 0, 1);

    public void resetFireDelay() {
        fireDelay = 2 + Math.random() * 4;
    }


    @Override
    public void tick(double delta) {

        ComponentGameLogic gameLogic = parent.getContext().getComponent(ComponentGameLogic.class);

        double speed = gameLogic.moveSpeed * delta;

        if (gameLogic != null) {
            if (gameLogic.enemyDir == 1) {
                parent.getTransform().x += speed;
            } else {
                parent.getTransform().x -= speed;
            }
        }

        if (parent.getTransform().x > 320 - 16) {
            gameLogic.changeEnemyDir(-1);
        }
        if (parent.getTransform().x < 16) {
            gameLogic.changeEnemyDir(1);
        }

        fireDelay -= delta;
        if (fireDelay < 0) {
            if (enemyType == EnemyType.shooter && gameLogic.enemiesCanShoot()) fireMissile();
            resetFireDelay();
        }
    }

    private void fireMissile() {
        List<GameObject> missiles = parent.getContext().getObjectsByTag(Constants.ENEMY_MISSILE);
        for (GameObject missile : missiles) {
            if (!missile.isActive()) {
                missile.setActive(true);
                missile.setVisible(true);
                missile.getTransform().set(parent.getTransform());
                break;
            }
        }
    }
    int armoredCol = Utils.floatToRgb(0.5f, 0.6f, 0.7f, 1);
    int shooterCol = Utils.floatToRgb(1, 0.5f, 0, 1);

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

        parent.addTag(Constants.ENEMY_TAG);

        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        tileSheet = parent.getContext().getObjectByType(TileSheet.class);
    }

    @Override
    public void draw() {
        if (!parent.isActive()) return;


        ComponentEnemy component = parent.getComponent(ComponentEnemy.class);
        if (component.enemyType == EnemyType.basic) garnet.getGraphics().setColor(basicCol);
        if (component.enemyType == EnemyType.armoured) garnet.getGraphics().setColor(armoredCol);
        if (component.enemyType == EnemyType.shooter) garnet.getGraphics().setColor(shooterCol);
        garnet.getGraphics().setScale(2);
        garnet.getGraphics().drawImage(tileSheet, (int) (parent.getTransform().x) - 8,
                (int) (parent.getTransform().y) - 8, 2, 2);

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

        Emitter emitter = new Emitter(parent.getTransform(), 0.2, explosionParticleTemplate);
        emitter.setEmitPerSecond(1500);

        parent.getContext().getObjectByType(ParticleManager.class).addEmitter(emitter);
    }

    @Override
    public GameObject collisionGetGameObject() {
        return parent;
    }
}
