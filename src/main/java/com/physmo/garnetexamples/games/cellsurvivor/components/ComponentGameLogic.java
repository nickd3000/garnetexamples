package com.physmo.garnetexamples.games.cellsurvivor.components;

import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.toolkit.Component;
import com.physmo.garnet.toolkit.scene.SceneManager;
import com.physmo.garnetexamples.games.cellsurvivor.scenes.SceneLevelUp;

public class ComponentGameLogic extends Component {

    public int playerLevel = 1;
    public int xp = 0;
    int currentScore = 0;
    ComponentPlayerCapabilities playerCapabilities;
    double waveDuration = 60;
    double waveTimer = waveDuration;
    int currentWave = 0;

    public int getCurrentWave() {
        return currentWave;
    }

    public int getPlayerLevel() {
        return playerLevel;
    }

    public void increaseXp(int amount) {
        xp += amount;
        int target = getXpToLevelUp();
        if (xp >= getXpToLevelUp()) {
            xp -= target;
            increaseLevel();
        }
    }

    public void increaseLevel() {
        playerLevel++;
//        System.out.println("Player levelled up to " + playerLevel);
//        playerCapabilities.increaseLevel(PlayerCapability.PROJECTILE_POWER);
//        playerCapabilities.increaseLevel(PlayerCapability.PROJECTILE_RATE);
//        playerCapabilities.increaseLevel(PlayerCapability.PROJECTILE_SPEED);
//        playerCapabilities.increaseLevel(PlayerCapability.PROJECTILE_MULTIPLIER);

        SceneManager.getSceneByName("levelUp").ifPresent(scene -> {
            ((SceneLevelUp) scene).setPlayerCapabilities(playerCapabilities);
        });

        SceneManager.pushSubScene("levelUp");
    }

    public int getXpToLevelUp() {
        return 5 + (playerLevel * 5);
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public void addToScore(int i) {
        setCurrentScore(getCurrentScore() + i);
    }

    @Override
    public void init() {
        currentScore = 0;
        playerCapabilities = parent.getContext().getComponent(ComponentPlayerCapabilities.class);
        if (playerCapabilities == null) throw new RuntimeException("No player capabilities");
    }

    @Override
    public void tick(double t) {
        waveTimer -= t;
        if (waveTimer < 0) {
            waveTimer = waveDuration;
            currentWave++;
            System.out.println("Wave " + currentWave);
        }
    }

    @Override
    public void draw(Graphics g) {

    }

    public int getEnemyCountForCurrentWave() {
        if (currentWave == 0) {
            return 85;
        } else if (currentWave == 1) {
            return 100;
        } else return 200;
    }
}
