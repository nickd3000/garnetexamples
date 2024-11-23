package com.physmo.garnetexamples.games.cellsurvivor.gamedata;

import java.util.ArrayList;
import java.util.List;

public class GDEnemy {
    int id;
    String name;
    double speed;
    int health;
    String sprite;
    List<GDEnemy> levels = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }
}
