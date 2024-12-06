package com.physmo.garnetexamples.games.cellsurvivor.components.items;

import com.physmo.garnetexamples.games.cellsurvivor.components.weapons.ValueChange;
import com.physmo.garnetexamples.games.cellsurvivor.components.weapons.WeaponStatType;

public interface Item {
    String getName();

    int getLevel();

    int getMaxLevel();

    void increaseLevel();

    ValueChange getWeaponModifierValueChange(WeaponStatType weaponStatType);
}
