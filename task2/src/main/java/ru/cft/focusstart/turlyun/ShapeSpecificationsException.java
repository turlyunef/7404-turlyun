package ru.cft.focusstart.turlyun;

/**
 * An exception is generated if there is no type of figure in enum Shapes,
 * or if the class does not match the shape with the necessary parameters in the constructor.
 * It can also be generated if the reading of parameters from a file fails or
 * the specifications is written to the output file.
 */
public class ShapeSpecificationsException extends Exception {

    /**
     * Constructor to save the reason for the exception
     *
     * @param message reason for the exception
     */
    ShapeSpecificationsException(String message) {
        super(message);
    }
}