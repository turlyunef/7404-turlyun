package ru.cft.focusstart.turlyun;

/**
 * Enumeration of possible types of shapes used for entry
 */
public enum Shapes {
    CIRCLE("Circle"), RECTANGLE("Rectangle"), TRIANGLE("Triangle");
    private final String shapeName;

    /**
     * Shape type creation constructor
     *
     * @param shapeName shape name
     */
    Shapes(String shapeName) {
        this.shapeName = shapeName;
    }

    /**
     * Get the shape name
     * @return the shape name
     */
    public String getShapeName() {
        return shapeName;
    }
}