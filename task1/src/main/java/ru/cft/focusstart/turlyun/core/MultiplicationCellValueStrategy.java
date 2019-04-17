package ru.cft.focusstart.turlyun.core;

/**
 * Implementation of the cell value creation strategy for multiplication tables.
 *
 * @see CellValueStrategy
 */
public class MultiplicationCellValueStrategy implements CellValueStrategy {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCellValue(int rowIndex, int columnIndex) {

        return String.valueOf(rowIndex * columnIndex);
    }
}