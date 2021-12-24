package com.physmo.garnettest.invaders;

import com.physmo.garnet.entity.Component;

public class ComponentPlayerMissile extends Component {
    double speed = 250;

    @Override
    public void tick(double delta) {
        parent.position.y -= speed * delta;

        if (parent.position.y < 0) parent.setActive(false);
    }
}
