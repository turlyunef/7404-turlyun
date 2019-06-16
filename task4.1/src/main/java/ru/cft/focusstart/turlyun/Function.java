package ru.cft.focusstart.turlyun;

/**
 * Class of function for calculations.
 */
class Function {

    /**
     * Returns the result of a calculation on a function.
     *
     * @param argument value over which the function is evaluated
     * @return result of a calculation on a function
     */
    static long function(long argument) {
        long localSum = 0;
        for (long j = 0; j < argument; j++) {
            localSum++;
        }

        return (long) Math.pow(localSum, 0.5);
    }
}