package com.physmo.garnetexamples.games.cellsurvivor.components.weapons;

/**
 * The ValueChange class represents a change in value that can be categorized as a whole number,
 * a percentage, or unknown type. This class provides functionality to create and combine
 * ValueChange instances.
 */
public class ValueChange {
    public static int TYPE_UNKNOWN = 0;
    public static int TYPE_WHOLE_NUMBER = 1;
    public static int TYPE_PERCENTAGE = 2;
    static ValueChange unchangedValue = new ValueChange();
    public int type = TYPE_UNKNOWN;
    public double value = 0;

    public static ValueChange createWholeNumberChange(double value) {
        ValueChange vc = new ValueChange();
        vc.type = TYPE_WHOLE_NUMBER;
        vc.value = value;
        return vc;
    }

    public static ValueChange createPercentageChange(double value) {
        ValueChange vc = new ValueChange();
        vc.type = TYPE_PERCENTAGE;
        vc.value = value;
        return vc;
    }

    public static ValueChange createUnchanged() {
        return unchangedValue;
    }

    /**
     * Combines the current ValueChange object with another ValueChange object.
     *
     * @param other the ValueChange object to be combined. It must have the same type as the current object.
     * @throws RuntimeException if the type of the other ValueChange object does not match the current object's type.
     */
    public void combine(ValueChange other) {
        if (other.type == TYPE_UNKNOWN) return;
        if (type == TYPE_UNKNOWN) type = other.type;
        if (other.type != type)
            throw new RuntimeException("value type mismatch: This type:" + type + " != " + other.type);
        value = value + other.value;
    }
}
