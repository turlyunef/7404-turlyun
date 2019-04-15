package ru.cft.focusstart.turlyun;

/**
 * Constants of positions of the maximum multiplier when entered by the user:
 * DEFAULT stores the default value, which will be returned by the method in case of incorrect user input
 * MINIMUM stores the minimum range value
 * MAXIMUM stores the maximum range value
 */
public enum ValuesOfMaximumMultiplier {
    DEFAULT(10), MINIMUM(1), MAXIMUM(32);
    private final int value;

    ValuesOfMaximumMultiplier(int value) {
        this.value = value;
    }

    public int getValue() {

        return value;
    }
}