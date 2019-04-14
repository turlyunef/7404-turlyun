package ru.cft.focusstart.turlyun;

import ru.cft.focusstart.turlyun.multiplicationTable.ConsoleCellSeparatorsStrategy;
import ru.cft.focusstart.turlyun.multiplicationTable.MultiplicationCellValueStrategy;
import ru.cft.focusstart.turlyun.multiplicationTable.MultiplicationConsoleTable;
import ru.cft.focusstart.turlyun.core.CellSeparatorStrategy;
import ru.cft.focusstart.turlyun.core.Table;
import ru.cft.focusstart.turlyun.core.CellValueStrategy;

/**
 * The program displays multiplication tables with a user-defined maximum multiplier.
 * If the user enters a number out of range, or not a number, the program uses the default value.
 * Table type (cell value generation algorithm) is set by the setCellValueStrategy method.
 * Cell separators is set using the setCellSeparatorsStrategy method.
 *
 * @author Turlyun Evgeny Fedorovich
 * @see Table
 * @see CellSeparatorStrategy
 * @see CellValueStrategy
 * @see MultiplicationConsoleTable
 */
public class Application {
    public static void main(String[] args) {
        Table table = new MultiplicationConsoleTable();
        int maxMultiplier = InputOperator.readNumberFromConsole("Enter a positive integer as the maximum multiplier to form the multiplication table.", 10, 1, 32);
        CellValueStrategy multiplicationCellValueStrategy = new MultiplicationCellValueStrategy(maxMultiplier);
        table.setCellValueStrategy(multiplicationCellValueStrategy);
        CellSeparatorStrategy lineSeparators = new ConsoleCellSeparatorsStrategy("-", "|", "+");
        table.setCellSeparatorsStrategy(lineSeparators);
        TableWriter.writeToConsole(table);
    }
}