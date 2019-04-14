package ru.cft.focusstart.turlyun.multiplicationTable;

import ru.cft.focusstart.turlyun.core.CellSeparatorStrategy;

/**
 * The strategy of creating table separators for a table displayed in the console
 *
 * @see CellSeparatorStrategy
 */
public class ConsoleCellSeparatorsStrategy implements CellSeparatorStrategy {
    private String cellLineSeparator;
    private String cellColumnSeparator;
    private String intersectionSeparator;

    /**
     * Separators are created by the user when configuring the Application program.
     *
     * @param cellRowSeparator     a row separator
     * @param cellColumnSeparator   a column separator
     * @param intersectionSeparator a intersection separator
     */
    public ConsoleCellSeparatorsStrategy(String cellRowSeparator, String cellColumnSeparator, String intersectionSeparator) {
        this.cellLineSeparator = cellRowSeparator;
        this.cellColumnSeparator = cellColumnSeparator;
        this.intersectionSeparator = intersectionSeparator;
    }

    @Override
    public String getCellColumnSeparator() {
        return this.cellColumnSeparator;
    }

    @Override
    public String getCellRowSeparator() {
        return this.cellLineSeparator;
    }

    @Override
    public String getIntersectionSeparator() {
        return this.intersectionSeparator;
    }
}