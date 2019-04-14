package ru.cft.focusstart.turlyun.core;

/**
 * The root interface in the <i>table hierarchy</i>.
 * Cell value and cell separators are set by external algorithms in classes that implement the CellValueStrategy and CellSeparatorStrategy interfaces.
 *
 * @see CellValueStrategy
 * @see CellSeparatorStrategy
 */

public interface Table {
    /**
     * Returns the table type.
     *
     * @return the strategy for generating cell values
     */
    CellValueStrategy getCellValueStrategy();

    /**
     * Sets the table type.
     *
     * @param cellValueStrategy the strategy for generating cell values
     */
    void setCellValueStrategy(CellValueStrategy cellValueStrategy);

    /**
     * Returns the data for generating cell separators.
     * @return the strategy for generating cell separators
     */
    CellSeparatorStrategy getCellSeparatorStrategy();

    /**
     * Sets the data for generating cell separators.
     *
     * @param cellSeparatorStrategy the strategy for generating cell separators
     */

    void setCellSeparatorsStrategy(CellSeparatorStrategy cellSeparatorStrategy);

    /**
     * Creates the value of a table cell, depending on the strategy for generating cell values.
     *
     * @param rowIndex row index from top to bottom
     * @param columnIndex column index from left to right
     * @return the resulting value from the rowIndex and columnIndex values
     */
    String getCellValue(int rowIndex, int columnIndex);

    /**
     * Creates the separator between cells in a table row.
     *
     * @return the separator in a table row
     */
    String getCellColumnSeparator();

    /**
     * Creates the separator between cells in a table column.
     *
     * @return the separator in a table column.
     */
    String getCellRowSeparator();

    /**
     * Creates the separator between cells in the intersection of the table
     *
     * @return the separator in the intersection of the table
     */
    String getIntersectionSeparator();

    /**
     * Before the generation of the table, its name will be displayed. Implement this method to correctly name the table.
     * @return the table name
     */
    String getTableName();
}