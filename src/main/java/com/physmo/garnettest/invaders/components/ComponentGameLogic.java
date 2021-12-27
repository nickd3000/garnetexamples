package com.physmo.garnettest.invaders.components;

import com.physmo.garnet.entity.Component;

public class ComponentGameLogic extends Component {
    public boolean dir = false;
    double timer = 0;

    @Override
    public void init() {

    }

    @Override
    public void tick(double delta) {
        timer += delta;
        if (timer > 5) {
            timer = 0;
            if (dir == false) dir = true;
            else dir = false;
        }
    }

}
