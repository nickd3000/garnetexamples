package com.physmo.garnettest.invaders.components;


import com.physmo.garnet.Garnet;
import com.physmo.garnet.Utils;
import com.physmo.garnet.input.Input;
import com.physmo.garnettest.invaders.Constants;
import com.physmo.garnettest.invaders.GameData;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.SceneManager;
import com.physmo.garnettoolkit.stateMachine.StateMachine;

import java.util.List;

public class ComponentGameLogic extends Component {
    public int enemyDir = 1;
    double timer = 0;
    double moveSpeed = 20;

    Garnet garnet;
    StateMachine levelState;
    String levelStateStart = "1";
    String levelStateRunning = "2";
    String levelStatePlayerHit = "3";
    String levelStateGameOver = "4";
    double stateTimer = 0;
    GameData gameData;
    private int pendingEnemyDir;

    public ComponentGameLogic() {
    }

    public void changeEnemyDir(int newDir) {
        pendingEnemyDir = newDir;
    }

    @Override
    public void init() {
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        gameData = SceneManager.getSharedContext().getObjectByType(GameData.class);

        initLevelStateMachine();
    }

    public void initLevelStateMachine() {

        levelState = new StateMachine();
        levelState.addState(levelStateStart, t -> {
            stateTimer += t;
            if (stateTimer > 3) {
                levelState.changeState(levelStateRunning);
            }
        });

        levelState.addTransition(StateMachine.ANY_STATE, StateMachine.ANY_STATE, t -> {
            System.out.println("any to any");
        });

        levelState.addTransition("", levelStateRunning, t -> {
            stateTimer = 0;
        });

        levelState.addState(levelStateRunning, t -> {
        });
        levelState.addState(levelStatePlayerHit, t -> {
            stateTimer += t;
            if (stateTimer > 3) {
                stateTimer = 0;
                if (gameData.lives > 0) {
                    levelState.changeState(levelStateRunning);
                } else {
                    levelState.changeState(levelStateGameOver);
                }
            }
        });
        levelState.addState(levelStateGameOver, t -> {
            System.out.println("game over state");
        });
        levelState.changeState(levelStateStart);
    }

    @Override
    public void tick(double delta) {
        timer += delta;

        levelState.tick(delta);

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

        List<GameObject> enemies = parent.getContext().getObjectsByTag(Constants.ENEMY_TAG);
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

    public boolean enemiesCanShoot() {
        return levelState.getCurrentStateName().equals(levelStateRunning);
    }

    public boolean playerCanMove() {
        String currentStateName = levelState.getCurrentStateName();
        return currentStateName.equals(levelStateRunning) | currentStateName.equals(levelStateStart);
    }

    public boolean playerIsFlashing() {
        return levelState.getCurrentStateName().equals(levelStateStart);
    }

    public boolean playerCanShoot() {
        return levelState.getCurrentStateName().equals(levelStateRunning);
    }

    public boolean playerIsInvincible() {
        return levelState.getCurrentStateName().equals(levelStateStart);
    }

    public void playerGotHit() {


        gameData.lives--;

        levelState.changeState(levelStatePlayerHit);

    }
}
