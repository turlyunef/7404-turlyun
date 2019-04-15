package ru.cft.focusstart.turlyun.core;

/**
 * A table type (a cell value generation algorithm) is set by this strategy.
 * A strategy can generate multiplication, division, addition, subtraction or any other value derived from row and column element.
 *
 * @see Table
 */
public interface CellValueStrategy {
    /**
     * Contains the main algorithm for creating the value of table cells.
     *
     * @param rowIndex    row index from top to bottom
     * @param columnIndex column index from left to right
     * @return the resulting value from the rowIndex and columnIndex values
     */
    String getCellValue(int rowIndex, int columnIndex);
}