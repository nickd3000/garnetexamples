package com.physmo.garnetexamples.games.cellsurvivor.components.items;

import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.toolkit.Component;
import com.physmo.garnetexamples.games.cellsurvivor.components.weapons.ValueChange;
import com.physmo.garnetexamples.games.cellsurvivor.components.weapons.WeaponStatType;

public class ShieldCharm extends Component implements Item {
    @Override
    public void init() {

    }

    @Override
    public void tick(double t) {

    }

    @Override
    public void draw(Graphics g) {

    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public void increaseLevel() {

    }

    @Override
    public ValueChange getWeaponModifierValueChange(WeaponStatType weaponStatType) {
        return null;
    }
}
