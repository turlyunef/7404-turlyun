package ru.cft.focusstart.turlyun.core;

/**
 * Multiplication table, designed to display in console form.
 * The table is automatically formatted for straight line columns.
 * The width of the cells is set under the maximum possible value of the multipliers.
 */
public class MultiplicationConsoleTable implements Table {
    private final CellValueStrategy cellValueStrategy;
    private final CellSeparatorStrategy cellSeparatorStrategy;
    private final int maxMultiplier;
    private final int rowElementCount;

    /**
     * Constructor with required parameters for creating multiplication table, designed to display in console form.
     *
     * @param maxMultiplier the maximum table multiplier
     */
    public MultiplicationConsoleTable(int maxMultiplier) {
        this.cellValueStrategy = new MultiplicationCellValueStrategy();
        this.cellSeparatorStrategy = new ConsoleCellSeparatorsStrategy();
        this.maxMultiplier = maxMultiplier;
        this.rowElementCount = (String.valueOf(maxMultiplier * maxMultiplier)).length();
    }

    @Override
    public String generateTable() {
        StringBuilder table = new StringBuilder();
        for (int i = 1; i <= maxMultiplier; i++) {
            table.append(generateRowWithValue(i));
            table.append(generateSeparatorsRow());
        }

        return String.valueOf(table);
    }

    /**
     * Creates a row of table values and separators between them.
     *
     * @param rowIndex the line index counting down
     * @return string of values and vertical delimiters as StringBuilder
     */
    private StringBuilder generateRowWithValue(int rowIndex) {
        StringBuilder rowWithValue = new StringBuilder();
        for (int j = 1; j < maxMultiplier; j++) {
            rowWithValue.append(getCellFormattedValue(cellValueStrategy.getCellValue(rowIndex, j)));
            rowWithValue.append(cellSeparatorStrategy.getHorizontalElementOfCellSeparator());
        }
        rowWithValue.append(getCellFormattedValue(cellValueStrategy.getCellValue(rowIndex, maxMultiplier)));
        rowWithValue.append("\n");

        return rowWithValue;
    }

    /**
     * Creates the string as StringBuilder of linear separators between values, as well as cell intersections.
     *
     * @return the row of the linear separators and the cell intersections
     */
    private StringBuilder generateSeparatorsRow() {
        StringBuilder separatorsRow = new StringBuilder();
        for (int j = 1; j < maxMultiplier; j++) {
            separatorsRow.append(generateCellRowSeparator());
            separatorsRow.append(cellSeparatorStrategy.getIntersectionSeparatorElement());
        }
        separatorsRow.append(generateCellRowSeparator());
        separatorsRow.append("\n");

        return separatorsRow;
    }

    /**
     * Creates the horizontal separators row of a single cell.
     * Based on the number of elements in one cell rowElementCount.
     * Intersection separators not included.
     *
     * @return the separators row of a single cell
     */
    private StringBuilder generateCellRowSeparator() {
        StringBuilder cellRowSeparator = new StringBuilder();
        for (int i = 0; i < rowElementCount; i++) {
            cellRowSeparator.append(cellSeparatorStrategy.getVerticalElementOfCellSeparator());
        }

        return cellRowSeparator;
    }

    /**
     * Generates the missing spaces before the value so that each cell is of the specified length.
     *
     * @param cellValue the cell value
     * @return the cell value with required spaces
     */
    private String getCellFormattedValue(String cellValue) {
        StringBuilder spacesBuilder = new StringBuilder();
        for (int i = 0; i < rowElementCount - cellValue.length(); i++) {
            spacesBuilder.append(" ");
        }

        return (spacesBuilder + cellValue);
    }
}