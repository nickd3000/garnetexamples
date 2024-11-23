package com.physmo.garnetexamples.games.cellsurvivor.gamedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameData {
    List<GDEnemy> enemies = new ArrayList<>();
    List<GDWeapon> weapons = new ArrayList<>();

    public GDWeapon getWeaponByName(String name) {
        for (GDWeapon w : weapons) {
            if (w.getName().equalsIgnoreCase(name)) return w;
        }
        return null;
    }


    public List<GDWeapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<GDWeapon> weapons) {
        this.weapons = weapons;
    }

    public List<GDEnemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<GDEnemy> enemies) {
        this.enemies = enemies;
    }

    public Optional<GDEnemy> getEnemyById(int id) {
        for (GDEnemy e : enemies) {
            if (e.getId() == id) return Optional.of(e);
        }
        return Optional.empty();
    }
}
