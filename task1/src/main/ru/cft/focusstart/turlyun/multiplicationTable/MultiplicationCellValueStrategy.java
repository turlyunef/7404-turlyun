package ru.cft.focusstart.turlyun.multiplicationTable;

import ru.cft.focusstart.turlyun.core.CellValueStrategy;

/**
 * Cell value creation strategy for multiplication tables
 *
 * @see CellValueStrategy
 */
public class MultiplicationCellValueStrategy implements CellValueStrategy {
    private int maxMultiplier;
    private int cellLength;

    public MultiplicationCellValueStrategy(int maxMultiplier) {
        this.maxMultiplier = maxMultiplier;
        this.cellLength = String.valueOf(maxMultiplier * maxMultiplier).length();
    }

    @Override
    public String getCellValue(int rowIndex, int columnIndex) {
        return String.valueOf(rowIndex * columnIndex);
    }

    @Override
    public int getTableRowCount() {
        return this.maxMultiplier;
    }

    @Override
    public int getTableColumnCount() {
        return this.maxMultiplier;
    }

    @Override
    public int getCellLength() {
        return this.cellLength;
    }
}