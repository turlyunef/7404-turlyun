package ru.cft.focusstart.turlyun.core;

/**
 * A strategy can generate multiplication, division, addition, subtraction or any other value derived from row and column elements.
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