package ru.cft.focusstart.turlyun;

/**
 * Constants value of the separators elements for cell separator strategy:
 * HORIZONTAL   the element of a row separator
 * VERTICAL     the element of a column separator
 * INTERSECTION the element of a intersection separator
 */
public enum SeparatorElements {
    HORIZONTAL("-"), VERTICAL("|"), INTERSECTION("+");
    private final String value;

    SeparatorElements(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}