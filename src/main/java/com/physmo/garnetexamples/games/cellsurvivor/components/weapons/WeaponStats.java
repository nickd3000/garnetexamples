package com.physmo.garnetexamples.games.cellsurvivor.components.weapons;

import com.physmo.garnetexamples.games.cellsurvivor.components.items.CombinedItemStats;
import com.physmo.garnetexamples.games.cellsurvivor.gamedata.GDWeapon;
import com.physmo.garnetexamples.games.cellsurvivor.gamedata.GDWeaponLevel;

import java.util.HashMap;
import java.util.Map;

public class WeaponStats {

    Map<WeaponStatType, WeaponStat> stats;
    double refreshTimer = 1;

    public WeaponStats() {
        stats = new HashMap<>();

        for (WeaponStatType value : WeaponStatType.values()) {
            stats.put(value, new WeaponStat());
        }
    }

    public WeaponStat get(WeaponStatType weaponStatType) {
        return stats.get(weaponStatType);
    }

    public void refreshStats(GDWeapon gdWeapon, int level, CombinedItemStats combinedItemStats) {
        stats.get(WeaponStatType.DAMAGE).baseValue = gdWeapon.getDamage();
        stats.get(WeaponStatType.SPEED).baseValue = gdWeapon.getSpeed();
        stats.get(WeaponStatType.COOLDOWN).baseValue = gdWeapon.getCooldown();
        stats.get(WeaponStatType.COUNT).baseValue = gdWeapon.getCount();
        stats.get(WeaponStatType.PIERCE).baseValue = gdWeapon.getPierce();
        stats.get(WeaponStatType.INTERVAL).baseValue = gdWeapon.getInterval();
        stats.get(WeaponStatType.KNOCK_BACK).baseValue = gdWeapon.getKnock_back();
        stats.get(WeaponStatType.DURATION).baseValue = gdWeapon.getDuration();

        for (WeaponStatType statType : WeaponStatType.values()) {
            refreshStat(statType, gdWeapon, level, combinedItemStats);
        }
    }

    public void refreshStatsOnTimeout(double t, GDWeapon gdWeapon, int level, CombinedItemStats combinedItemStats) {
        refreshTimer -= t;
        if (refreshTimer < 0) {
            refreshTimer += 3;
            refreshStats(gdWeapon, level, combinedItemStats);
        }
    }

    private void refreshStat(WeaponStatType statType, GDWeapon gdWeapon, int level, CombinedItemStats combinedItemStats) {

        ValueChange overallChange = new ValueChange();

        for (GDWeaponLevel gdWeaponLevel : gdWeapon.getLevels()) {
            if (gdWeaponLevel.getId() > level) continue;
            String[] split = gdWeaponLevel.getEffect().trim().split(" ");
            if (split.length == 3) {

                if (!split[0].equalsIgnoreCase(statType.name())) continue;
                ValueChange valueChange = getValueChange(split);
                overallChange.combine(valueChange);
            }
        }

        // Get item affects
        ValueChange itemEffect = combinedItemStats.getWeaponModifierValueChange(statType);
        if (itemEffect.type != ValueChange.TYPE_UNKNOWN) {
            overallChange.combine(itemEffect);
        }

        double baseValue = stats.get(statType).baseValue;

        if (overallChange.type == ValueChange.TYPE_PERCENTAGE) {
            double scale = (100 + overallChange.value) / 100.0;
            stats.get(statType).value = baseValue * scale;
            stats.get(statType).percentageChange = scale;
        }

        if (overallChange.type == ValueChange.TYPE_WHOLE_NUMBER) {
            stats.get(statType).value = baseValue + overallChange.value;
            stats.get(statType).percentageChange = 0;
        }

        if (overallChange.type == ValueChange.TYPE_UNKNOWN) {
            stats.get(statType).value = baseValue;
        }

        //System.out.println(statType.name() + "   base:" + stats.get(statType).baseValue + " value:" + stats.get(statType).value + " " + stats.get(statType).percentageChange + " type:" + overallChange.type);
    }

    /**
     * Calculates the value change based on the provided input array.
     * The input array should contain three elements: the direction of change ("up" or "down"),
     * a string representation of the change type (percentage or whole number), and the value of the change.
     *
     * @param split an array containing the direction, type, and value of the change
     * @return a ValueChange object representing the calculated value change
     */
    public ValueChange getValueChange(String[] split) {
        if (split[2].contains("%")) {
            double val = Double.parseDouble(split[2].replace("%", ""));
            if (split[1].equals("up")) {
                return ValueChange.createPercentageChange(val);
            } else {
                return ValueChange.createPercentageChange(-val);
            }
        } else {
            double val = Double.parseDouble(split[2]);
            if (split[1].equals("up")) {
                return ValueChange.createWholeNumberChange(val);
            } else {
                return ValueChange.createWholeNumberChange(-val);
            }
        }
    }
}
