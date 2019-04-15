package ru.cft.focusstart.turlyun;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class of user input methods in console.
 * @see ValuesOfMaximumMultiplier
 */
class InputOperator {
    /**
     * Requests a user to enter a number into the console.
     * If the user enters a number out of range, or not a number, the program uses the default value.
     *
     * @return user entered number
     */
    static int readNumberFromConsole() {
        int number;
        System.out.printf("The number should be in the range [%d,%d]: ", ValuesOfMaximumMultiplier.MINIMUM.getValue(), ValuesOfMaximumMultiplier.MAXIMUM.getValue());
        try {
            number = (new Scanner(System.in).nextInt());
        } catch (InputMismatchException e) {
            System.out.printf("You entered not a number, the default value is %d\n", ValuesOfMaximumMultiplier.DEFAULT.getValue());
            number = ValuesOfMaximumMultiplier.DEFAULT.getValue();
        } catch (IllegalStateException | NoSuchElementException e) {
            System.out.println(e.getMessage());
            System.out.printf("The default value is %d\n", ValuesOfMaximumMultiplier.DEFAULT.getValue());
            number = ValuesOfMaximumMultiplier.DEFAULT.getValue();
        }
        if ((number >= ValuesOfMaximumMultiplier.MINIMUM.getValue()) & (number <= ValuesOfMaximumMultiplier.MAXIMUM.getValue())) {

            return number;
        }
        System.out.printf("You entered a number outside the range [%d,%d]\n", ValuesOfMaximumMultiplier.MINIMUM.getValue(), ValuesOfMaximumMultiplier.MAXIMUM.getValue());
        System.out.printf("The default value is %d\n", ValuesOfMaximumMultiplier.DEFAULT.getValue());

        return ValuesOfMaximumMultiplier.DEFAULT.getValue();
    }
}