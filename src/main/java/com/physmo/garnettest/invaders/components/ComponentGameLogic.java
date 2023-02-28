package com.physmo.garnettest.invaders.components;


import com.physmo.garnet.Garnet;
import com.physmo.garnet.input.Input;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.SceneManager;

public class ComponentGameLogic extends Component {
    public boolean dir = false;
    double timer = 0;

    SceneManager sceneManager;
    Garnet garnet;

    public ComponentGameLogic(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @Override
    public void init() {
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
    }

    @Override
    public void tick(double delta) {
        timer += delta;
        if (timer > 5) {
            timer = 0;
            if (dir == false) dir = true;
            else dir = false;
        }

        if (garnet.getInput().isFirstPress(Input.VirtualButton.MENU)) {
            sceneManager.pushSubScene("pause");
        }
    }

}
