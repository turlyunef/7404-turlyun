package ru.cft.focusstart.turlyun.core;

/**
 * Multiplication table, designed to display in console.
 * The table is automatically formatted for straight line columns.
 * The width of the cells is set to the maximum possible value of the result of multiplying the maximum factors.
 */
public class MultiplicationConsoleTable implements Table {
    private final CellValueStrategy cellValueStrategy;
    private final CellSeparatorsStrategy cellSeparatorsStrategy;
    private final int maxMultiplier;
    private final int rowElementsCount;

    /**
     * The constructor with the necessary maximum multiplier parameter to create a multiplication table for display in the form of a console.
     *
     * @param maxMultiplier the maximum table multiplier
     */
    public MultiplicationConsoleTable(int maxMultiplier) {
        this.cellValueStrategy = new MultiplicationCellValueStrategy();
        this.cellSeparatorsStrategy = new ConsoleCellSeparatorsStrategy();
        this.maxMultiplier = maxMultiplier;
        this.rowElementsCount = (String.valueOf(maxMultiplier * maxMultiplier)).length();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateTable() {
        StringBuilder table = new StringBuilder();
        for (int i = 1; i <= maxMultiplier; i++) {
            table.append(generateRowWithValues(i));
            table.append(generateSeparatorsRow());
        }

        return String.valueOf(table);
    }

    /**
     * Creates a row of table values and separators between them.
     *
     * @param rowIndex the line index counting down
     * @return string of values and vertical separators as StringBuilder
     */
    private StringBuilder generateRowWithValues(int rowIndex) {
        StringBuilder rowWithValues = new StringBuilder();
        for (int j = 1; j < maxMultiplier; j++) {
            rowWithValues.append(getCellFormattedValue(cellValueStrategy.getCellValue(rowIndex, j)));
            rowWithValues.append(cellSeparatorsStrategy.getVerticalElementOfCellSeparator());
        }
        rowWithValues.append(getCellFormattedValue(cellValueStrategy.getCellValue(rowIndex, maxMultiplier)));
        rowWithValues.append("\n");

        return rowWithValues;
    }

    /**
     * Creates the string as StringBuilder of linear separators between cells, as well as field intersections.
     *
     * @return the row of the linear separators and the cells intersections
     */
    private StringBuilder generateSeparatorsRow() {
        StringBuilder separatorsRow = new StringBuilder();
        for (int j = 1; j < maxMultiplier; j++) {
            separatorsRow.append(generateHorizontalOneCellSeparator());
            separatorsRow.append(cellSeparatorsStrategy.getIntersectionSeparatorElement());
        }
        separatorsRow.append(generateHorizontalOneCellSeparator());
        separatorsRow.append("\n");

        return separatorsRow;
    }

    /**
     * Creates the horizontal separators row of a single field.
     * Based on the number of elements in one field rowElementsCount.
     * Intersection separator not included.
     *
     * @return the separators row of a single field
     */
    private StringBuilder generateHorizontalOneCellSeparator() {
        StringBuilder horizontalOneCellSeparator = new StringBuilder();
        for (int i = 0; i < rowElementsCount; i++) {
            horizontalOneCellSeparator.append(cellSeparatorsStrategy.getHorizontalElementOfCellSeparator());
        }

        return horizontalOneCellSeparator;
    }

    /**
     * Generates the missing spaces before the value so that each field is of the specified length.
     *
     * @param cellValue the field value
     * @return the field value with required spaces
     */
    private String getCellFormattedValue(String cellValue) {
        StringBuilder spacesBuilder = new StringBuilder();
        for (int i = 0; i < rowElementsCount - cellValue.length(); i++) {
            spacesBuilder.append(" ");
        }

        return (spacesBuilder + cellValue);
    }
}