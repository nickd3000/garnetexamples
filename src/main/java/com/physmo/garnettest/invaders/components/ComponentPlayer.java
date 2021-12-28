package com.physmo.garnettest.invaders.components;

import com.physmo.garnet.entity.Component;
import com.physmo.garnet.entity.Entity;
import com.physmo.garnet.input.Input;

import java.util.List;

public class ComponentPlayer extends Component {

    double speed = 100;
    double bulletCoolDown = 0;

    double leftWall = 8;
    double rightWall = 300-8;

    @Override
    public void init() {

    }

    @Override
    public void tick(double delta) {
        if (Input.isPressed(Input.VirtualButton.RIGHT)) {
            parent.position.x += speed * delta;
            if (parent.position.x > rightWall) parent.position.x = rightWall;
        }
        if (Input.isPressed(Input.VirtualButton.LEFT)) {
            parent.position.x -= speed * delta;
            if (parent.position.x < leftWall) parent.position.x = leftWall;
        }
        if (Input.isPressed(Input.VirtualButton.FIRE1)) {
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
                break;
            }
        }

    }

}
