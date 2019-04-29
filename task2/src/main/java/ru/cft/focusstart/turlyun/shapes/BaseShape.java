package ru.cft.focusstart.turlyun.shapes;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Each shape has a name, perimeter and area.
 * This class is inherited by all other shapes.
 */
public abstract class BaseShape implements Shape {
    private final Map<String, String> shapeSpecifications = new LinkedHashMap<>();

    @Override
    public Map<String, String> getShapeSpecifications() {
        return shapeSpecifications;
    }

    /**
     * Generates a base specifications of a shape from name, area, perimeter
     */
    protected void generateShapeSpecifications() {
        appendShapeName();
        appendShapeArea();
        appendShapePerimeter();
    }

    /**
     * Generates a shape name and appends this to the specifications
     */
    protected abstract void appendShapeName();

    /**
     * Generates a shape area and appends this to the specifications
     */
    protected abstract void appendShapeArea();

    /**
     * Generates a perimeter of the shape and appends this to the specifications
     */
    protected abstract void appendShapePerimeter();

    /**
     * Appends to the specifications the name of the shape parameter and its value
     *
     * @param key   name of the shape parameter
     * @param value value of this shape parameter
     */
    void appendShapeSpecifications(String key, String value) {
        this.shapeSpecifications.put(key, value);
    }
}