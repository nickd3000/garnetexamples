package com.physmo.garnetexamples.entitytest;

import com.physmo.garnet.entity.Component;

public class WallBounceComponent extends Component {

    @Override
    public void init() {

    }

    public void tick(double delta) {
        double width = 600;
        double height = 400;

        if (parent.position.x < 0) {
            parent.position.x = 0 - parent.position.x;
            parent.velocity.x *= -1;
        }

        if (parent.position.x > width) {
            parent.position.x = width - (parent.position.x - width);
            parent.velocity.x *= -1;
        }
        if (parent.position.y < 0) {
            parent.position.y = 0 - parent.position.y;
            parent.velocity.y *= -1;
        }

        if (parent.position.y > height) {
            parent.position.y = height - (parent.position.y - height);
            parent.velocity.y *= -1;
        }
    }

}
