package com.physmo.garnettest.entitytest;

import com.physmo.garnet.entity.Component;

public class FrictionComponent extends Component {

    @Override
    public void init() {

    }

    public void tick(double delta) {
        double friction = 0.5;
        parent.velocity.x -= parent.velocity.x * friction * delta;
        parent.velocity.y -= parent.velocity.y * friction * delta;
    }
}
