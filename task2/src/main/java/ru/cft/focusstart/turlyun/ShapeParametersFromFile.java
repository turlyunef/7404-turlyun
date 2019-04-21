package ru.cft.focusstart.turlyun;

/**
 * The class is designed to store the shape parameters necessary to generate its specification.
 */
public class ShapeParametersFromFile {
    private String shapeName;
    private int[] shapeParameters;

    /**
     * Gets the name of the shape
     *
     * @return the name of the shape
     */
    public String getShapeName() {
        return shapeName;
    }

    /**
     * Gets the parameters of the shape
     *
     * @return the parameters of the shape
     */
    public int[] getShapeParameters() {
        return shapeParameters;
    }

    /**
     * Sets the name of the shape
     *
     * @param shapeName the name of the shape
     */
    public void setShapeName(String shapeName) {
        this.shapeName = shapeName;
    }

    /**
     * Sets the parameters of the shape
     *
     * @param shapeParameters the parameters of the shape
     */
    public void setShapeParameters(int[] shapeParameters) {
        this.shapeParameters = shapeParameters;
    }
}