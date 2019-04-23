package ru.cft.focusstart.turlyun;

/**
 * An exception is generated if there is no class corresponding to the name of the figure,
 * or if the class does not match the figure with the necessary parameters in the constructor.
 * It can also be generated if the reading of parameters from a file fails or the specification is written to the output file.
 */
public class ShapeSpecificationsException extends Exception {

    /**
     * Constructor to save the reason for the exception
     *
     * @param message the reason for the exception
     */
    public ShapeSpecificationsException(String message) {
        super(message);
    }
}