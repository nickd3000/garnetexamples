package com.physmo.garnetexamples.games.cellsurvivor.gamedata;

import java.util.ArrayList;
import java.util.List;

public class GDWeapon {
    int id;
    String name;
    double damage;
    double speed;
    double count;
    double cooldown;
    double interval;
    double knock_back;
    double duration;
    double pierce;
    List<GDWeaponLevel> levels = new ArrayList<>();

    public double getPierce() {
        return pierce;
    }

    public void setPierce(double pierce) {
        this.pierce = pierce;
    }

    public double getInterval() {
        return interval;
    }

    public void setInterval(double interval) {
        this.interval = interval;
    }

    public double getKnock_back() {
        return knock_back;
    }

    public void setKnock_back(double knock_back) {
        this.knock_back = knock_back;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

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

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public double getCooldown() {
        return cooldown;
    }

    public void setCooldown(double cooldown) {
        this.cooldown = cooldown;
    }

    public List<GDWeaponLevel> getLevels() {
        return levels;
    }

    public void setLevels(List<GDWeaponLevel> levels) {
        this.levels = levels;
    }

}
