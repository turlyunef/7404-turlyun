package ru.cft.focusstart.turlyun.shapes;

import java.util.LinkedHashMap;

/**
 * Each shape has a name, perimeter and area.
 * This class is inherited by all other shapes.
 */
public abstract class SomeShape implements Shape {

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkedHashMap<String, String> getShapeSpecifications() {
        LinkedHashMap<String, String> shapeSpecifications = new LinkedHashMap<>();
        shapeSpecifications.putAll(getShapeName());
        shapeSpecifications.putAll(getShapeArea());
        shapeSpecifications.putAll(getPerimeterOfTheFigure());

        return (shapeSpecifications);
    }

    /**
     * Returns a LinkedHashMap with a shape name.
     *
     * @return a LinkedHashMap with a shape name
     */
    public abstract LinkedHashMap<String, String> getShapeName();

    /**
     * Returns a LinkedHashMap with a shape area.
     *
     * @return a LinkedHashMap with a shape area
     */
    public abstract LinkedHashMap<String, String> getShapeArea();

    /**
     * Returns a LinkedHashMap with a shape perimeter.
     *
     * @return a LinkedHashMap with a shape perimeter
     */
    public abstract LinkedHashMap<String, String> getPerimeterOfTheFigure();
}