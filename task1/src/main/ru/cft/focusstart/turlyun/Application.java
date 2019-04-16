package ru.cft.focusstart.turlyun;

import ru.cft.focusstart.turlyun.core.CellSeparatorStrategy;
import ru.cft.focusstart.turlyun.core.CellValueStrategy;
import ru.cft.focusstart.turlyun.core.MultiplicationConsoleTable;
import ru.cft.focusstart.turlyun.core.Table;

/**
 * The program displays multiplication tables with a user-defined maximum multiplier.
 * If the user enters a number out of range, or not a number, the program uses the default value.
 *
 * @author Turlyun Evgeny Fedorovich
 * @see Table
 * @see CellSeparatorStrategy
 * @see CellValueStrategy
 * @see MultiplicationConsoleTable
 */
class Application {
    public static void main(String[] args) {
        System.out.println("Enter a positive integer as the maximum multiplier to form the multiplication table.");
        int maxMultiplier = InputOperator.readNumberFromConsole();
        Table table = new MultiplicationConsoleTable(maxMultiplier);
        System.out.println("Multiplication table will be:");
        System.out.println(table.generateTable());
    }
}