package ru.cft.focusstart.turlyun.core;

/**
 * The root interface in the <i>table hierarchy</i>.
 * Cell value and cell separators are set by external algorithms in classes that implement the CellValueStrategy and CellSeparatorStrategy interfaces.
 */

public interface Table {
    /**
     * Returns the finished table as a string.
     *
     * @return table as a string
     */
    String generateTable();
}