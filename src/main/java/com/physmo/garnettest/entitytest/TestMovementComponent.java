package com.physmo.garnettest.entitytest;

import com.physmo.garnet.entity.Component;

public class TestMovementComponent extends Component {


    public void tick(double delta) {

        parent.position.x += parent.velocity.x * delta;
        parent.position.y += parent.velocity.y * delta;

        if (Math.random() * delta < 0.0001) {
            parent.velocity.x += (Math.random() - 0.5) * 100;
            parent.velocity.y += (Math.random() - 0.5) * 100;
        }
    }

}
