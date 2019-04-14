package ru.cft.focusstart.turlyun.multiplicationTable;

import ru.cft.focusstart.turlyun.core.CellSeparatorStrategy;
import ru.cft.focusstart.turlyun.core.CellValueStrategy;
import ru.cft.focusstart.turlyun.core.Table;

/**
 * The multiplication table with the specified maximum multiplier. The cell length is adjusted automatically to the length of the largest number in the table.
 *
 * @see Table
 * @see MultiplicationCellValueStrategy
 * @see ConsoleCellSeparatorsStrategy
 */
public class MultiplicationConsoleTable implements Table {
    private CellValueStrategy cellValueStrategy;
    private CellSeparatorStrategy cellSeparatorStrategy;

    @Override
    public CellValueStrategy getCellValueStrategy() {
        return this.cellValueStrategy;
    }

    @Override
    public void setCellValueStrategy(CellValueStrategy cellValueStrategy) {
        this.cellValueStrategy = cellValueStrategy;
    }

    @Override
    public void setCellSeparatorsStrategy(CellSeparatorStrategy cellSeparatorStrategy) {
        this.cellSeparatorStrategy = cellSeparatorStrategy;
    }

    @Override
    public CellSeparatorStrategy getCellSeparatorStrategy() {
        return this.cellSeparatorStrategy;
    }

    @Override
    public String getCellValue(int rowIndex, int columnIndex) {
        return this.cellValueStrategy.getCellValue(rowIndex, columnIndex);
    }

    @Override
    public String getCellColumnSeparator() {
        return this.cellSeparatorStrategy.getCellColumnSeparator();
    }

    @Override
    public String getCellRowSeparator() {
        return this.cellSeparatorStrategy.getCellRowSeparator();
    }

    @Override
    public String getIntersectionSeparator() {
        return this.cellSeparatorStrategy.getIntersectionSeparator();
    }

    @Override
    public String getTableName() {

        return "The multiplication table";
    }
}