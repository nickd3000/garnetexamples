package com.physmo.garnettest.invaders.components;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.color.Color;
import com.physmo.garnet.color.ColorSupplierLinear;
import com.physmo.garnet.curve.CurveType;
import com.physmo.garnet.curve.StandardCurve;
import com.physmo.garnet.input.Input;
import com.physmo.garnet.particle.ParticleTemplate;
import com.physmo.garnet.spritebatch.Sprite2D;
import com.physmo.garnet.spritebatch.SpriteBatch;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.SceneManager;

import java.util.List;

public class ComponentPlayer extends Component {

    double speed = 100;
    double bulletCoolDown = 0;

    double leftWall = 8;
    double rightWall = 300 - 8;
    ParticleTemplate shootParticleTemplate;
    SpriteBatch spriteBatch;
    Garnet garnet;
    SceneManager sceneManager;

    public ComponentPlayer(SpriteBatch spriteBatch, Garnet garnet, SceneManager sceneManager) {
        this.spriteBatch = spriteBatch;
        this.garnet = garnet;
        this.sceneManager = sceneManager;
    }

    @Override
    public void init() {
        shootParticleTemplate = new ParticleTemplate();
        shootParticleTemplate.setLifeTime(0.2, 0.8);
        shootParticleTemplate.setSpeed(10, 50);
        shootParticleTemplate.setPositionJitter(1.1);
        shootParticleTemplate.setColorSupplier(new ColorSupplierLinear(new Color(1, 1, 1, 0.5f), new Color(1, 1, 1, 0)));
        shootParticleTemplate.setSpeedCurve(new StandardCurve(CurveType.LINE_DOWN));
    }

    @Override
    public void tick(double delta) {

        if (garnet.getInput().isPressed(Input.VirtualButton.RIGHT)) {
            parent.getTransform().x += speed * delta;
            if (parent.getTransform().x > rightWall) parent.getTransform().x = rightWall;
        }
        if (garnet.getInput().isPressed(Input.VirtualButton.LEFT)) {
            parent.getTransform().x -= speed * delta;
            if (parent.getTransform().x < leftWall) {
                parent.getTransform().x = leftWall;
                //sceneManager.setActiveScene("menu");
            }
        }
        if (garnet.getInput().isPressed(Input.VirtualButton.FIRE1)) {
            fireMissile();
        }

        if (bulletCoolDown > 0) bulletCoolDown -= delta;
    }

    private void fireMissile() {
        if (bulletCoolDown > 0) return;

        List<GameObject> player_missiles = parent.getContext().getObjectsByTag("player_missile");
        for (GameObject player_missile : player_missiles) {
            if (!player_missile.isActive()) {
                player_missile.setActive(true);
                player_missile.getTransform().x = parent.getTransform().x;
                player_missile.getTransform().y = parent.getTransform().y;
                bulletCoolDown = 0.2;

                //Emitter emitter = new Emitter(parent.getTransform(), 0.2, shootParticleTemplate);
                //emitter.setEmitPerSecond(150);
                //parentState.getParticleManager().addEmitter(emitter);

                break;
            }
        }


    }

    @Override
    public void draw() {
        //System.out.println("draw player");
        spriteBatch.add(Sprite2D.build(
                (int) (parent.getTransform().x) - 8,
                (int) (parent.getTransform().y) - 8,
                16, 16, 0, 32, 16, 16).addColor(1, 0, 1, 1));
    }
}
