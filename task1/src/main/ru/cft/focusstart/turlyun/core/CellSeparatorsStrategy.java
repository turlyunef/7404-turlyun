package ru.cft.focusstart.turlyun.core;

/**
 * A strategy for creating table separator elements.
 * Cells can be separated any String symbol.
 * For cells, there should be horizontal, vertical and intersection elements of a cells separators.
 *
 * @see Table
 */
public interface CellSeparatorsStrategy {
    /**
     * Creates the element of a separator between cells in a table column.
     *
     * @return the element of column separator
     */
    String getHorizontalElementOfCellSeparator();

    /**
     * Creates the element of a separator between cells in a table row.
     *
     * @return the element of a row separator
     */
    String getVerticalElementOfCellSeparator();

    /**
     * Creates the element of a cells intersection.
     *
     * @return the intersection separator
     */
    String getIntersectionSeparatorElement();
}