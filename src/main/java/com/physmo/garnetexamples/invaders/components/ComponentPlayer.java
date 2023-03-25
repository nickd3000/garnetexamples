package com.physmo.garnetexamples.invaders.components;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.Utils;
import com.physmo.garnet.input.Input;
import com.physmo.garnet.spritebatch.TileSheet;
import com.physmo.garnetexamples.invaders.Constants;
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


public class ComponentPlayer extends Component implements Collidable {

    public static final double BULLET_COOL_DOWN = 0.2;
    static int playerColor = Utils.floatToRgb(0.3f, 1f, 0.2f, 1f);
    static int playerColorB = Utils.floatToRgb(1f, 0.2f, 1f, 1f);

    double speed = 100;
    double bulletCoolDown = 0;
    double leftWall = 8;
    double rightWall = 300 - 8;
    ParticleTemplate shootParticleTemplate;

    Garnet garnet;
    ParticleManager particleManager;

    double flashTimer = 0;
    boolean flashOn = false;

    ComponentGameLogic gameLogic;
    TileSheet tileSheet;

    public ComponentPlayer() {

    }

    @Override
    public void init() {
        shootParticleTemplate = new ParticleTemplate();
        shootParticleTemplate.setLifeTime(0.2, 0.8);
        shootParticleTemplate.setSpeed(10, 50);
        shootParticleTemplate.setPositionJitter(1.1);
        shootParticleTemplate.setColorSupplier(new ColorSupplierLinear(new Color(1, 0, 1, 0.5f), new Color(0, 1, 1, 0)));
        shootParticleTemplate.setSpeedCurve(new StandardCurve(CurveType.LINE_DOWN));

        particleManager = parent.getContext().getObjectByType(ParticleManager.class);

        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);

        parent.addTag(Constants.PLAYER_TAG);

        CollisionSystem collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);
        collisionSystem.addCollidable(this);
        tileSheet = parent.getContext().getObjectByType(TileSheet.class);
        gameLogic = parent.getContext().getComponent(ComponentGameLogic.class);


    }

    @Override
    public void tick(double delta) {

        boolean canMove = gameLogic.playerCanMove();
        boolean canShoot = gameLogic.playerCanShoot();

        if (canMove && garnet.getInput().isPressed(Input.VirtualButton.RIGHT)) {
            parent.getTransform().x += speed * delta;
            if (parent.getTransform().x > rightWall) parent.getTransform().x = rightWall;
        }
        if (canMove && garnet.getInput().isPressed(Input.VirtualButton.LEFT)) {
            parent.getTransform().x -= speed * delta;
            if (parent.getTransform().x < leftWall) {
                parent.getTransform().x = leftWall;
            }
        }
        if (canShoot && garnet.getInput().isPressed(Input.VirtualButton.FIRE1)) {
            fireMissile();
        }

        if (bulletCoolDown > 0) bulletCoolDown -= delta;

        flashTimer += delta;
        if (flashTimer > 0.3) {
            flashTimer = 0;
            flashOn = !flashOn;
        }
    }

    private void fireMissile() {
        if (bulletCoolDown > 0) return;

        GameObject playerMissile = playerMissileFactory();

        if (playerMissile != null) {
            parent.getContext().add(playerMissile);
            playerMissile.setActive(true);
            playerMissile.getTransform().set(parent.getTransform());
            bulletCoolDown = BULLET_COOL_DOWN;

            Emitter emitter = new Emitter(parent.getTransform(), 0.2, shootParticleTemplate);
            emitter.setEmitPerSecond(150);
            particleManager.addEmitter(emitter);
        }
    }

    public GameObject playerMissileFactory() {
        GameObject missile = new GameObject("player_missile");
        missile.setActive(false);
        missile.setVisible(true);
        missile.addComponent(new ComponentPlayerMissile());
        missile.addTag("pausable");
        //context.add(missile);
        return missile;
    }

    @Override
    public void draw() {
        int drawCol = playerColor;
        boolean invincible = gameLogic.playerIsInvincible() | gameLogic.playerIsInvincible();
        if (flashOn && invincible) drawCol = playerColorB;

        garnet.getGraphics().setScale(2);
        garnet.getGraphics().setColor(drawCol);
        garnet.getGraphics().drawImage(tileSheet, (int) (parent.getTransform().x) - 8,
                (int) (parent.getTransform().y) - 8, 0, 2);
    }

    @Override
    public Rect collisionGetRegion() {
        Rect rect = new Rect(parent.getTransform().x - 8, parent.getTransform().y, 16, 10);
        return rect;
    }

    @Override
    public void collisionCallback(CollisionPacket collisionPacket) {
        GameObject otherObject = collisionPacket.targetEntity.collisionGetGameObject();

        if (otherObject.getTags().contains(Constants.ENEMY_MISSILE)) {
            gameLogic.playerGotHit();
        }
    }

    @Override
    public GameObject collisionGetGameObject() {
        return parent;
    }
}