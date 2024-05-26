package com.physmo.garnetexamples.games.invaders.components;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.Utils;
import com.physmo.garnet.input.InputAction;
import com.physmo.garnetexamples.games.invaders.Constants;
import com.physmo.garnetexamples.games.invaders.InvadersEntityFactory;
import com.physmo.garnetexamples.games.invaders.Resources;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.color.ColorSupplierLinear;
import com.physmo.garnettoolkit.color.ColorUtils;
import com.physmo.garnettoolkit.curve.CurveType;
import com.physmo.garnettoolkit.curve.StandardCurve;
import com.physmo.garnettoolkit.particle.Emitter;
import com.physmo.garnettoolkit.particle.ParticleManager;
import com.physmo.garnettoolkit.particle.ParticleTemplate;
import com.physmo.garnettoolkit.scene.SceneManager;
import com.physmo.garnettoolkit.simplecollision.ColliderComponent;
import com.physmo.garnettoolkit.simplecollision.CollisionSystem;


public class ComponentPlayer extends Component {

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
    Resources resources;
    CollisionSystem collisionSystem;

    public ComponentPlayer() {

    }

    @Override
    public void init() {
        shootParticleTemplate = new ParticleTemplate();
        shootParticleTemplate.setLifeTime(0.2, 0.8);
        shootParticleTemplate.setSpeed(10, 50);
        shootParticleTemplate.setPositionJitter(1.1);
        shootParticleTemplate.setColorSupplier(new ColorSupplierLinear(ColorUtils.asRGBA(1, 0, 1, 0.5f), ColorUtils.asRGBA(0, 1, 1, 0)));
        shootParticleTemplate.setSpeedCurve(new StandardCurve(CurveType.LINE_DOWN));

        particleManager = parent.getContext().getObjectByType(ParticleManager.class);

        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);

        parent.addTag(Constants.PLAYER_TAG);

        collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);

        gameLogic = parent.getContext().getComponent(ComponentGameLogic.class);

        resources = SceneManager.getSharedContext().getObjectByType(Resources.class);

        ColliderComponent colliderComponent = parent.getComponent(ColliderComponent.class);
        colliderComponent.setCallbackEnter(col -> {
            if (col.hasTag(Constants.ENEMY_MISSILE)) {
                System.out.println("player got hit");
            }
        });

        //soundLaser = garnet.getSound().loadSound(Utils.getPathForResource(this,"sounds/laserShoot-3.wav"));
    }

    //int soundLaser;
    @Override
    public void tick(double delta) {

        boolean canMove = gameLogic.playerCanMove();
        boolean canShoot = gameLogic.playerCanShoot();

        if (canMove && garnet.getInput().isActionKeyPressed(InputAction.RIGHT)) {
            parent.getTransform().x += speed * delta;
            if (parent.getTransform().x > rightWall) parent.getTransform().x = rightWall;
        }
        if (canMove && garnet.getInput().isActionKeyPressed(InputAction.LEFT)) {
            parent.getTransform().x -= speed * delta;
            if (parent.getTransform().x < leftWall) {
                parent.getTransform().x = leftWall;
            }
        }
        if (canShoot && garnet.getInput().isActionKeyPressed(InputAction.FIRE1)) {
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

        GameObject playerMissile = InvadersEntityFactory.addPlayerMissile(parent.getContext(), collisionSystem, parent.getTransform().x, parent.getTransform().y);

        float soundVolume = 0.5f;
        float soundPan = 0.0f;

        soundPan = Utils.remapRange((float) parent.getTransform().x, 0, 200, -1, 1);

        garnet.getSound().playSound(resources.soundIdLaser, soundVolume, soundPan);

        if (playerMissile != null) {

            bulletCoolDown = BULLET_COOL_DOWN;

            Emitter emitter = new Emitter(parent.getTransform(), 0.2, shootParticleTemplate);
            emitter.setEmitPerSecond(150);
            particleManager.addEmitter(emitter);
        }
    }


    @Override
    public void draw() {
        int drawCol = playerColor;
        boolean invincible = gameLogic.playerIsInvincible() | gameLogic.playerIsInvincible();
        if (flashOn && invincible) drawCol = playerColorB;


        garnet.getGraphics().setColor(drawCol);
        garnet.getGraphics().drawImage(resources.getSpriteTilesheet(), (int) (parent.getTransform().x) - 8,
                (int) (parent.getTransform().y) - 8, 0, 2);
    }

}
