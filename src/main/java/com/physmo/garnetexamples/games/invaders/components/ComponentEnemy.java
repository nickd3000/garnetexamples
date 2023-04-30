package com.physmo.garnetexamples.games.invaders.components;


import com.physmo.garnet.Garnet;
import com.physmo.garnet.Utils;
import com.physmo.garnet.drawablebatch.TileSheet;
import com.physmo.garnetexamples.games.invaders.Constants;
import com.physmo.garnetexamples.games.invaders.EnemyType;
import com.physmo.garnetexamples.games.invaders.GameData;
import com.physmo.garnetexamples.games.invaders.InvadersEntityFactory;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.SceneManager;
import com.physmo.garnettoolkit.color.Color;
import com.physmo.garnettoolkit.color.ColorSupplierLinear;
import com.physmo.garnettoolkit.curve.CurveType;
import com.physmo.garnettoolkit.curve.StandardCurve;
import com.physmo.garnettoolkit.particle.Emitter;
import com.physmo.garnettoolkit.particle.ParticleManager;
import com.physmo.garnettoolkit.particle.ParticleTemplate;
import com.physmo.garnettoolkit.simplecollision.ColliderComponent;
import com.physmo.garnettoolkit.simplecollision.CollisionSystem;

public class ComponentEnemy extends Component {

    public EnemyType enemyType;
    GameData gameData;
    double fireDelay;
    double health;
    ParticleTemplate explosionParticleTemplate;

    TileSheet tileSheet;
    Garnet garnet;
    int basicCol = Utils.floatToRgb(0, 1, 0, 1);
    int armoredCol = Utils.floatToRgb(0.5f, 0.6f, 0.7f, 1);
    int shooterCol = Utils.floatToRgb(1, 0.5f, 0, 1);
    CollisionSystem collisionSystem;

    public ComponentEnemy(EnemyType enemyType) {
        this.enemyType = enemyType;
    }

    @Override
    public void tick(double delta) {

        ComponentGameLogic gameLogic = parent.getContext().getComponent(ComponentGameLogic.class);

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
        InvadersEntityFactory.addEnemyMissile(parent.getContext(), collisionSystem, parent.getTransform().x, parent.getTransform().y);
    }

    @Override
    public void init() {

        collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);

        gameData = SceneManager.getSharedContext().getObjectByType(GameData.class);

        resetFireDelay();
        health = 1;

        explosionParticleTemplate = new ParticleTemplate();
        explosionParticleTemplate.setLifeTime(0.2, 3);
        explosionParticleTemplate.setSpeed(30, 50);
        explosionParticleTemplate.setPositionJitter(2.1);
        explosionParticleTemplate.setColorSupplier(new ColorSupplierLinear(Color.YELLOW, new Color(1, 0, 0, 0)));
        explosionParticleTemplate.setSpeedCurve(new StandardCurve(CurveType.LINE_DOWN));

        parent.addTag(Constants.ENEMY_TAG);

        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        tileSheet = parent.getContext().getObjectByType(TileSheet.class);

        ColliderComponent colliderComponent = parent.getComponent(ColliderComponent.class);
        colliderComponent.setCallbackEnter(col -> {
            if (col.hasTag(Constants.PLAYER_MISSILE)) {
                handleBulletHit(1);
            }
        });
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
        garnet.getGraphics().setScale(2);
        garnet.getGraphics().drawImage(tileSheet, (int) (parent.getTransform().x) - 8,
                (int) (parent.getTransform().y) - 8, 2, 2);

    }
//
//    @Override
//    public Rect collisionGetRegion() {
//        Rect rect = new Rect();
//        rect.set(parent.getTransform().x - 7, parent.getTransform().y - 7, 14, 14);
//        return rect;
//    }
//
//    @Override
//    public void collisionCallback(CollisionPacket collisionPacket) {
//        GameObject otherObject = collisionPacket.targetEntity.collisionGetGameObject();
//
//        if (otherObject.getTags().contains(Constants.PLAYER_MISSILE)) {
//            handleBulletHit(1);
//        }
//    }

    private void handleBulletHit(double bulletDamage) {
        double damageScale = 1;

        if (enemyType == EnemyType.armoured) damageScale = 1.0f / 3.0f;

        health -= bulletDamage * damageScale;
        if (health <= 0) handleDying();
    }

    private void handleDying() {
        //collisionSystem.removeCollidable(this);
        ColliderComponent colliderComponent = parent.getComponent(ColliderComponent.class);
        collisionSystem.removeCollidable(colliderComponent);

        parent.destroy();

        gameData.currentScore++;

        Emitter emitter = new Emitter(parent.getTransform(), 0.2, explosionParticleTemplate);
        emitter.setEmitPerSecond(1500);

        parent.getContext().getObjectByType(ParticleManager.class).addEmitter(emitter);
    }

//    @Override
//    public void proximityCallback(RelativeObject relativeObject) {
//
//    }
//
//    @Override
//    public GameObject collisionGetGameObject() {
//        return parent;
//    }
}
