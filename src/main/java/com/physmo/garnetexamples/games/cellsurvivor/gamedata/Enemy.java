package com.physmo.garnetexamples.games.cellsurvivor.gamedata;

public class Enemy {
    int id;
    String name;
    double speed;
    int health;
    String sprite;

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

/*
    <enemy id="0" name="Mummy" speed="0.8" health="200">
        <sprite>10,7</sprite>
    </enemy>
 */
