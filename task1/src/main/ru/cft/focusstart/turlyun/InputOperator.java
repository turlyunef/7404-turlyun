package ru.cft.focusstart.turlyun;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class of user input methods in console
 */
public class InputOperator {
    /**
     * Requests a user to enter a number into the console.
     * If the user enters a number out of range, or not a number, the program uses the default value.
     *
     * @param messageForUser the message for the user with the information which number should be entered
     * @param defaultValue   the default value, which will be returned by the method in case of incorrect user input
     * @param minValue       the minimum range value
     * @param maxValue       the maximum range value
     * @return user entered number
     */
    public static int readNumberFromConsole(String messageForUser, int defaultValue, int minValue, int maxValue) {
        int number;
        System.out.println(messageForUser);
        System.out.printf("The number should be in the range [%d,%d]: ", minValue, maxValue);
        try {
            number = (new Scanner(System.in).nextInt());
        } catch (InputMismatchException e) {
            System.out.printf("You entered not a number, the default value is %d\n", defaultValue);
            number = defaultValue;
        } catch (IllegalStateException | NoSuchElementException e) {
            System.out.println(e.getMessage());
            System.out.printf("The default value is %d\n", defaultValue);
            number = defaultValue;
        }
        if ((number >= minValue) && (number <= maxValue)) {

            return number;
        }
        System.out.printf("You entered a number outside the range [%d,%d]\n", minValue, maxValue);
        System.out.printf("The default value is %d\n", defaultValue);

        return defaultValue;
    }
}