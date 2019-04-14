package ru.cft.focusstart.turlyun.core;

/**
 * Cells can be separated in any way.
 * For cells, there should be horizontal, vertical, and intersection separators
 *
 * @see Table
 */
public interface CellSeparatorStrategy {
    /**
     * Creates the separator between cells in a table row.
     *
     * @return the column separator
     */
    String getCellColumnSeparator();

    /**
     * Creates the separator between cells in a table column
     *
     * @return the row separator
     */
    String getCellRowSeparator();

    /**
     * Creates the separator between cells in a table intersection
     *
     * @return the intersection separator
     */
    String getIntersectionSeparator();
}