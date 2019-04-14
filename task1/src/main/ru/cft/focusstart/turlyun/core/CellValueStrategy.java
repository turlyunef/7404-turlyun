package ru.cft.focusstart.turlyun.core;

/**
 * A table can generate multiplication, division, addition, subtraction, and any other algorithm of interaction between a row and a column.
 *
 * @see Table
 */
public interface CellValueStrategy {
    /**
     * Contains the main mechanism for creating the value of table cells
     *
     * @param rowIndex    row index from top to bottom
     * @param columnIndex column index from left to right
     * @return the resulting value from the rowIndex and columnIndex values
     */
    String getCellValue(int rowIndex, int columnIndex);

    /**
     * The number of elements in a column for a given cell value strategy
     *
     * @return the number of elements in a column
     */
    int getTableRowCount();

    /**
     * The number of elements in a row for a given cell value strategy
     *
     * @return the number of elements in a row
     */
    int getTableColumnCount();

    /**
     * The table should be displayed correctly, and specifically straight columns.
     * Therefore, each table cell must contain the same number of characters, regardless of the value of the cell.
     *
     * @return cell length
     */
    int getCellLength();
}