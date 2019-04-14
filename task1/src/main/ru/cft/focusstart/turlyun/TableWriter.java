package ru.cft.focusstart.turlyun;

import ru.cft.focusstart.turlyun.core.Table;

/**
 * Table generation class.
 */
class TableWriter {

    /**
     * Generates and displays a table in the console.
     *
     * @param table the table to be displayed in the console
     */
    static void writeToConsole(Table table) {
        System.out.println(table.getTableName() + " will be:");
        for (int i = 1; i <= table.getCellValueStrategy().getTableRowCount(); i++) {
            writeValuesRow(table, i);
            writeSeparatorsRow(table);
        }
    }

    /**
     * Prints a row of cell values and separators between them to the console.
     *
     * @param table    the table to be displayed in the console
     * @param rowIndex the row number from top to bottom
     */
    private static void writeValuesRow(Table table, int rowIndex) {
        int tableColumnCount = table.getCellValueStrategy().getTableColumnCount();
        for (int j = 1; j < tableColumnCount; j++) {
            System.out.print(getCellFormattedValue(table, rowIndex, j));
            System.out.print(table.getCellColumnSeparator());
        }
        System.out.println(getCellFormattedValue(table, rowIndex, tableColumnCount));
    }

    /**
     * Prints a row of cell separators to the console.
     *
     * @param table the table to be displayed in the console
     */
    private static void writeSeparatorsRow(Table table) {
        StringBuilder cellRowSeparator = createCellRowSeparator(table);
        cellRowSeparator.append(table.getIntersectionSeparator());
        for (int j = 1; j < table.getCellValueStrategy().getTableColumnCount(); j++) {
            System.out.print(cellRowSeparator);
        }
        System.out.println(createCellRowSeparator(table));
    }

    /**
     * Creates the row of horizontal separators for a single cell
     *
     * @param table the table to be displayed in the console
     * @return the row of horizontal separators for a single cell
     */
    private static StringBuilder createCellRowSeparator(Table table) {
        StringBuilder cellRowSeparator = new StringBuilder();
        for (int i = 0; i < table.getCellValueStrategy().getCellLength(); i++) {
            cellRowSeparator.append(table.getCellRowSeparator());
        }

        return cellRowSeparator;
    }

    /**
     * Generates the missing spaces before the value so that each cell is of the specified length.
     *
     * @param table       the table to be displayed in the console
     * @param rowIndex    the row number from top to bottom
     * @param columnIndex the column number from left to right
     * @return formatted cell value
     */
    private static String getCellFormattedValue(Table table, int rowIndex, int columnIndex) {
        String cellValue = table.getCellValue(rowIndex, columnIndex);
        StringBuilder spacesBuilder = new StringBuilder();
        for (int i = 0; i < table.getCellValueStrategy().getCellLength() - cellValue.length(); i++) {
            spacesBuilder.append(" ");
        }

        return (spacesBuilder + cellValue);
    }
}