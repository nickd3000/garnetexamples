package com.physmo.garnetexamples.games.cellsurvivor.gamedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameData {
    List<Enemy> enemies = new ArrayList<>();

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public Optional<Enemy> getEnemyById(int id) {
        for (Enemy e : enemies) {
            if (e.getId() == id) return Optional.of(e);
        }
        return Optional.empty();
    }
}
