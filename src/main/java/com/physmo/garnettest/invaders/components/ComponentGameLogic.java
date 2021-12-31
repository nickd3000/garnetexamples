package com.physmo.garnettest.invaders.components;

import com.physmo.garnet.entity.Component;
import com.physmo.garnet.input.Input;

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

        if (parent.garnet.getInput().isFirstPress(Input.VirtualButton.MENU)) {
            parent.garnet.pushSubState("pause");
        }
    }

}
