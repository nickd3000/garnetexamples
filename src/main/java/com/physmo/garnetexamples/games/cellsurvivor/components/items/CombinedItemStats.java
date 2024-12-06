package com.physmo.garnetexamples.games.cellsurvivor.components.items;

import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.toolkit.Component;
import com.physmo.garnetexamples.games.cellsurvivor.components.weapons.ValueChange;
import com.physmo.garnetexamples.games.cellsurvivor.components.weapons.WeaponStatType;

import java.util.HashMap;
import java.util.Map;

public class CombinedItemStats extends Component {

    double refreshTimer = 1;

    Map<WeaponStatType, ValueChange> weaponStatChange = new HashMap<>();
    boolean initialized = false;

    public ValueChange getWeaponModifierValueChange(WeaponStatType weaponStatType) {
        if (!initialized) init();

        return weaponStatChange.get(weaponStatType);

    }

    public void refresh() {
        clearBaseStats();

        for (Component component : parent.getComponents()) {
            if (component instanceof Item item) {
                for (WeaponStatType weaponStatType : WeaponStatType.values()) {
                    ValueChange modifier = item.getWeaponModifierValueChange(weaponStatType);
                    if (modifier.type == 0) continue;
                    weaponStatChange.get(weaponStatType).combine(modifier);
                }

            }
        }

    }

    public void clearBaseStats() {
        for (WeaponStatType weaponStatType : WeaponStatType.values()) {
            if (weaponStatType.isInteger()) {
                weaponStatChange.put(weaponStatType, ValueChange.createWholeNumberChange(0));
            } else {
                weaponStatChange.put(weaponStatType, ValueChange.createPercentageChange(0));
            }
        }
    }

    @Override
    public void init() {
        initialized = true;

        clearBaseStats();

        // Set test values
        //weaponStatChange.get(WeaponStatType.COUNT).value = 1;
    }

    @Override
    public void tick(double t) {

        refreshTimer -= t;
        if (refreshTimer < 0) {
            refreshTimer += 1;
            refresh();
            //System.out.println("refresh item stats");
        }

    }

    @Override
    public void draw(Graphics g) {

    }
}
