package ru.cft.focusstart.turlyun;

/**
 * The class is designed to store the shape parameters necessary to generate its specifications.
 */
public class InputShapeParameters {
    private String shapeName;
    private int[] shapeParameters;

    /**
     * Gets the name of the shape
     *
     * @return the name of the shape
     */
    public String getShapeName() {
        return this.shapeName;
    }

    /**
     * Gets the parameters of the shape
     *
     * @return parameters of the shape
     */
    public int[] getShapeParameters() {
        return this.shapeParameters;
    }

    /**
     * Sets the name of the shape
     *
     * @param shapeName name of the shape
     */
    public void setShapeName(String shapeName) {
        this.shapeName = shapeName;
    }

    /**
     * Sets the parameters of the shape
     *
     * @param shapeParameters parameters of the shape
     */
    public void setShapeParameters(int[] shapeParameters) {
        this.shapeParameters = shapeParameters;
    }
}