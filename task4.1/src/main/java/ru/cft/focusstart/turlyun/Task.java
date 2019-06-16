package ru.cft.focusstart.turlyun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.UnaryOperator;

/**
 * The class of tasks prepared for calculating function in several threads in parallel.
 * The calculation is performed on the argument, the value of which goes from the minimum to the maximum specified.
 */
class Task implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Task.class);
    private final int minArgument;
    private final int maxArgument;
    private final UnaryOperator<Long> function;
    private long result;

    /**
     * Creates a task with its parameters.
     *
     * @param minArgument minimum value of the argument from which the calculation will start
     * @param maxArgument maximum value of the argument up to which the calculation will be performed
     * @param function    function on which calculations will be performed
     */
    Task(int minArgument, int maxArgument, UnaryOperator<Long> function) {
        this.minArgument = minArgument;
        this.maxArgument = maxArgument;
        this.function = function;
    }

    /**
     * Starts the calculation.
     */
    @Override
    public void run() {
        sumResult();
        displayResult();
    }

    /**
     * Returns the result of a calculation.
     *
     * @return result of a calculation
     */
    long getResult() {

        return this.result;
    }

    /**
     * Sums the result of the function calculating from the minimum to the maximum value of the argument.
     */
    private void sumResult() {
        for (long i = this.minArgument; i <= this.maxArgument; i++) {
            this.result += this.function.apply(i);
            displayCompletionPercentage(i);
        }
    }

    /**
     * Displays the result of the calculation.
     */
    private void displayResult() {
        log.info(String.format("Result from %s is %d.", Thread.currentThread().getName(), getResult()));
    }

    /**
     * Displays a notification in the log when the calculation is performed for 33% of arguments and 66% of arguments.
     *
     * @param i current argument value
     */
    private void displayCompletionPercentage(long i) {
        if (i == (maxArgument - minArgument) / 3 + minArgument) {
            log.debug("33% counted.");
        }
        if (i == (maxArgument - minArgument) * 2 / 3 + minArgument) {
            log.debug("66% counted.");
        }
    }
}