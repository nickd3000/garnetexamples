package com.physmo.garnetexamples.games.invaders.components;


import com.physmo.garnet.Garnet;
import com.physmo.garnet.Utils;
import com.physmo.garnet.toolkit.Component;
import com.physmo.garnet.toolkit.color.ColorSupplierLinear;
import com.physmo.garnet.toolkit.color.ColorUtils;
import com.physmo.garnet.toolkit.curve.CurveType;
import com.physmo.garnet.toolkit.curve.StandardCurve;
import com.physmo.garnet.toolkit.particle.Emitter;
import com.physmo.garnet.toolkit.particle.ParticleManager;
import com.physmo.garnet.toolkit.particle.ParticleTemplate;
import com.physmo.garnet.toolkit.scene.SceneManager;
import com.physmo.garnet.toolkit.simplecollision.ColliderComponent;
import com.physmo.garnet.toolkit.simplecollision.CollisionSystem;
import com.physmo.garnetexamples.games.invaders.Constants;
import com.physmo.garnetexamples.games.invaders.EnemyType;
import com.physmo.garnetexamples.games.invaders.GameData;
import com.physmo.garnetexamples.games.invaders.InvadersEntityFactory;
import com.physmo.garnetexamples.games.invaders.Resources;

public class ComponentEnemy extends Component {

    public EnemyType enemyType;

    double fireDelay;
    double health;

    ParticleTemplate explosionParticleTemplate;
    GameData gameData;
    Garnet garnet;
    CollisionSystem collisionSystem;
    Resources resources;

    int basicCol = Utils.floatToRgb(0, 1, 0, 1);
    int armoredCol = Utils.floatToRgb(0.5f, 0.6f, 0.7f, 1);
    int shooterCol = Utils.floatToRgb(1, 0.5f, 0, 1);

    int soundExplosionId;
    int soundShieldHitId;
    int soundEnemyFireId;
    ComponentGameLogic gameLogic;

    public ComponentEnemy(EnemyType enemyType) {
        this.enemyType = enemyType;
    }

    @Override
    public void init() {
        gameLogic = parent.getContext().getComponent(ComponentGameLogic.class);

        collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);

        gameData = SceneManager.getSharedContext().getObjectByType(GameData.class);

        resetFireDelay();
        health = 1;

        explosionParticleTemplate = new ParticleTemplate();
        explosionParticleTemplate.setLifeTime(0.2, 3);
        explosionParticleTemplate.setSpeed(30, 50);
        explosionParticleTemplate.setPositionJitter(2.1);
        explosionParticleTemplate.setColorSupplier(new ColorSupplierLinear(ColorUtils.YELLOW, ColorUtils.asRGBA(1, 0, 0, 0)));
        explosionParticleTemplate.setSpeedCurve(new StandardCurve(CurveType.LINE_DOWN));

        parent.addTag(Constants.ENEMY_TAG);

        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);

        ColliderComponent colliderComponent = parent.getComponent(ColliderComponent.class);
        colliderComponent.setCallbackEnter(col -> {
            if (col.hasTag(Constants.PLAYER_MISSILE)) {
                handleBulletHit(1);
            }
        });

        resources = SceneManager.getSharedContext().getObjectByType(Resources.class);
        soundExplosionId = resources.soundIdExplosion;
        soundShieldHitId = resources.soundIdShieldHit;
        soundEnemyFireId = resources.soundIdEnemyFire;
    }

    @Override
    public void tick(double delta) {


        double speed = gameLogic.moveSpeed * delta;

        if (gameLogic.enemyDir == 1) {
            parent.getTransform().x += speed;
        } else {
            parent.getTransform().x -= speed;
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
        garnet.getSound().playSound(soundEnemyFireId);
        InvadersEntityFactory.addEnemyMissile(parent.getContext(), collisionSystem, parent.getTransform().x, parent.getTransform().y);
    }

    public void resetFireDelay() {
        fireDelay = 2 + Math.random() * 4;
    }

    @Override
    public void draw() {
        if (!parent.isActive()) return;

        ComponentEnemy component = parent.getComponent(ComponentEnemy.class);
        if (component.enemyType == EnemyType.basic) garnet.getGraphics().setColor(basicCol);
        if (component.enemyType == EnemyType.armoured) garnet.getGraphics().setColor(armoredCol);
        if (component.enemyType == EnemyType.shooter) garnet.getGraphics().setColor(shooterCol);
        garnet.getGraphics().drawImage(resources.getSpriteTilesheet(), (int) (parent.getTransform().x) - 8, (int) (parent.getTransform().y) - 8, 2, 2);

    }

    private void handleBulletHit(double bulletDamage) {
        double damageScale = 1;

        if (enemyType == EnemyType.armoured) damageScale = 1.0f / 3.0f;

        health -= bulletDamage * damageScale;
        if (health <= 0) handleDying();
        else garnet.getSound().playSound(soundShieldHitId);
    }

    private void handleDying() {
        ColliderComponent colliderComponent = parent.getComponent(ColliderComponent.class);
        collisionSystem.removeCollidable(colliderComponent);

        parent.destroy();

        gameData.currentScore++;

        Emitter emitter = new Emitter(parent.getTransform(), 0.2, explosionParticleTemplate);
        emitter.setEmitPerSecond(1500);

        parent.getContext().getObjectByType(ParticleManager.class).addEmitter(emitter);

        garnet.getSound().playSound(soundExplosionId);
    }

}
