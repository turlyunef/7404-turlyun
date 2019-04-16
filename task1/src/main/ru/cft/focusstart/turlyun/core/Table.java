package ru.cft.focusstart.turlyun.core;

/**
 * The root interface in the <i>table hierarchy</i>.
 * Cell values are sets by external algorithm in class that implement the CellValueStrategy interface.
 * Elements of the cell separators sets by external algorithm in class that implement the CellSeparatorsStrategy interface.
 */

public interface Table {
    /**
     * Returns the finished table as a string.
     *
     * @return table as a string
     */
    String generateTable();
}