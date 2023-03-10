package com.physmo.garnettest.invaders.components;


import com.physmo.garnet.Garnet;
import com.physmo.garnet.Utils;
import com.physmo.garnet.input.Input;
import com.physmo.garnettest.invaders.Constants;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.SceneManager;

import java.util.List;

public class ComponentGameLogic extends Component {
    public int enemyDir = 1;
    double timer = 0;
    double moveSpeed = 20;

    Garnet garnet;
    private int pendingEnemyDir;

    public ComponentGameLogic() {
    }

    public void changeEnemyDir(int newDir) {
        pendingEnemyDir = newDir;
    }

    @Override
    public void init() {
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
    }

    @Override
    public void tick(double delta) {
        timer += delta;

        if (pendingEnemyDir != enemyDir) {
            enemyDir = pendingEnemyDir;
        }

        calculateEnemySpeed();

        if (garnet.getInput().isFirstPress(Input.VirtualButton.MENU)) {
            System.out.println("MENU");
            SceneManager.pushSubScene("pause");
        }
    }

    private void calculateEnemySpeed() {

        List<GameObject> enemies = parent.getContext().getObjectsByTag(Constants.ENEMY);
        int numEnemies = 0;
        for (GameObject enemy : enemies) {
            if (enemy.isActive()) numEnemies++;
        }

        double nspeed = 0;
        if (numEnemies < 40) nspeed = 0.25;
        if (numEnemies < 30) nspeed = 0.5;
        if (numEnemies < 20) nspeed = 0.75;
        if (numEnemies < 10) nspeed = 1;

        moveSpeed = Utils.lerp(20, 50, nspeed);
    }

}
