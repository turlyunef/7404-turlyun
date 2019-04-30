package ru.cft.focusstart.turlyun;

/**
 * Enumeration of possible types of shapes used for entry
 */
public enum Figure {
    CIRCLE("Circle", 1), RECTANGLE("Rectangle", 2),
    TRIANGLE("Triangle", 3);
    private final String name;
    private final int parametersCount;

    /**
     * Figure type creation constructor
     *
     * @param name       shape name
     * @param parametersCount number of input parameters
     */
    Figure(String name, int parametersCount) {
        this.name = name;
        this.parametersCount = parametersCount;
    }

    /**
     * Returns the shape name
     *
     * @return shape name
     */
    public String getName() {

        return name;
    }

    /**
     * Returns number of input parameters
     *
     * @return number of input parameters
     */
    public int getParametersCount() {

        return parametersCount;
    }
}