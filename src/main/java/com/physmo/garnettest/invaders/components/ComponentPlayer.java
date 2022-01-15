package com.physmo.garnettest.invaders.components;

import com.physmo.garnet.color.Color;
import com.physmo.garnet.color.ColorSupplierLinear;
import com.physmo.garnet.curve.CurveType;
import com.physmo.garnet.curve.StandardCurve;
import com.physmo.garnet.entity.Component;
import com.physmo.garnet.entity.Entity;
import com.physmo.garnet.input.Input;
import com.physmo.garnet.particle.Emitter;
import com.physmo.garnet.particle.ParticleTemplate;

import java.util.List;

public class ComponentPlayer extends Component {

    double speed = 100;
    double bulletCoolDown = 0;

    double leftWall = 8;
    double rightWall = 300 - 8;
    ParticleTemplate shootParticleTemplate;

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
        if (parent.garnet.getInput().isPressed(Input.VirtualButton.RIGHT)) {
            parent.position.x += speed * delta;
            if (parent.position.x > rightWall) parent.position.x = rightWall;
        }
        if (parent.garnet.getInput().isPressed(Input.VirtualButton.LEFT)) {
            parent.position.x -= speed * delta;
            if (parent.position.x < leftWall) {
                parent.position.x = leftWall;
                parent.garnet.switchActiveState("menu");
            }
        }
        if (parent.garnet.getInput().isPressed(Input.VirtualButton.FIRE1)) {
            fireMissile();
        }

        if (bulletCoolDown > 0) bulletCoolDown -= delta;
    }

    private void fireMissile() {
        if (bulletCoolDown > 0) return;

        List<Entity> player_missiles = parent.getGameState().getEntitiesByTag("player_missile");
        for (Entity player_missile : player_missiles) {
            if (!player_missile.getActive()) {
                player_missile.setActive(true);
                player_missile.position.x = parent.position.x;
                player_missile.position.y = parent.position.y;
                bulletCoolDown = 0.2;

                Emitter emitter = new Emitter(parent.position, 0.2, shootParticleTemplate);
                emitter.setEmitPerSecond(150);
                parentState.getParticleManager().addEmitter(emitter);

                break;
            }
        }


    }

}
