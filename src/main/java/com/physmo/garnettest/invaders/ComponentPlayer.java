package com.physmo.garnettest.invaders;

import com.physmo.garnet.Input;
import com.physmo.garnet.entity.Component;
import com.physmo.garnet.entity.Entity;

import java.util.List;

public class ComponentPlayer extends Component {

    double speed = 100;
    double bulletCooldown = 0;

    @Override
    public void tick(double delta) {
        if (Input.isPressed(Input.VirtualButton.RIGHT)) {
            parent.position.x += speed * delta;
        }
        if (Input.isPressed(Input.VirtualButton.LEFT)) {
            parent.position.x -= speed * delta;
        }
        if (Input.isPressed(Input.VirtualButton.FIRE1)) {
            fireMissile();
        }

        if (bulletCooldown > 0) bulletCooldown -= delta;
    }

    private void fireMissile() {
        if (bulletCooldown > 0) return;

        List<Entity> player_missiles = parent.getGameState().getEntityByTag("player_missile");
        for (Entity player_missile : player_missiles) {
            if (!player_missile.getActive()) {
                player_missile.setActive(true);
                player_missile.position.x = parent.position.x;
                player_missile.position.y = parent.position.y;
                bulletCooldown = 0.2;
                break;
            }
        }

    }

}
