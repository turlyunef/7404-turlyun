package ru.cft.focusstart.turlyun;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * User input number class in console.
 */
class InputOperator {
    private static final int DEFAULT_VALUE_OF_MAXIMUM_MULTIPLIER = 10;
    private static final int MINIMUM_VALUE_OF_MAXIMUM_MULTIPLIER = 1;
    private static final int MAXIMUM_VALUE_OF_MAXIMUM_MULTIPLIER = 32;

    /**
     * Requests a user to enter a number into the console.
     * If the user enters a number out of range, or not a number, the program uses the default value of maximum multiplier.
     *
     * @return number entered by user
     */
    static int readNumberFromConsole() {
        int number;
        System.out.printf("The number should be in the range [%d, %d]: ", MINIMUM_VALUE_OF_MAXIMUM_MULTIPLIER, MAXIMUM_VALUE_OF_MAXIMUM_MULTIPLIER);
        try {
            number = (new Scanner(System.in).nextInt());
        } catch (InputMismatchException e) {
            System.out.printf("You entered not a number, the default value is %d\n", DEFAULT_VALUE_OF_MAXIMUM_MULTIPLIER);
            number = DEFAULT_VALUE_OF_MAXIMUM_MULTIPLIER;
        } catch (IllegalStateException | NoSuchElementException e) {
            System.out.println(e.getMessage());
            System.out.printf("The default value is %d\n", DEFAULT_VALUE_OF_MAXIMUM_MULTIPLIER);
            number = DEFAULT_VALUE_OF_MAXIMUM_MULTIPLIER;
        }
        if ((number < MINIMUM_VALUE_OF_MAXIMUM_MULTIPLIER) || (number > MAXIMUM_VALUE_OF_MAXIMUM_MULTIPLIER)) {
            System.out.printf("You entered a number outside the range [%d,%d]\n", MINIMUM_VALUE_OF_MAXIMUM_MULTIPLIER, MAXIMUM_VALUE_OF_MAXIMUM_MULTIPLIER);
            System.out.printf("The default value is %d\n", DEFAULT_VALUE_OF_MAXIMUM_MULTIPLIER);

            return DEFAULT_VALUE_OF_MAXIMUM_MULTIPLIER;
        }

        return number;
    }
}