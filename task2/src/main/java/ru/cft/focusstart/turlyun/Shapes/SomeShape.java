package ru.cft.focusstart.turlyun.Shapes;

import java.util.LinkedHashMap;

public abstract class SomeShape implements Shape {
    @Override
    public LinkedHashMap<String, String> getShapeSpecifications() {
        LinkedHashMap<String, String> shapeSpecifications = new LinkedHashMap<>();
        shapeSpecifications.putAll(getShapeName());
        shapeSpecifications.putAll(getShapeArea());
        shapeSpecifications.putAll(getPerimeterOfTheFigure());

        return (shapeSpecifications);
    }

    public abstract LinkedHashMap<String, String> getShapeName();

    public abstract LinkedHashMap<String, String> getShapeArea();

    public abstract LinkedHashMap<String, String> getPerimeterOfTheFigure();
}