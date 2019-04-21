package ru.cft.focusstart.turlyun.Shapes;

import java.util.LinkedHashMap;

public class Circle extends SomeShape {
    private int radius;

    public Circle(int[] numbers) {
        this.radius = numbers[0];
    }

    @Override
    public LinkedHashMap<String, String> getShapeSpecifications() {
        LinkedHashMap<String, String> shapeSpecifications = new LinkedHashMap<>(super.getShapeSpecifications());
        shapeSpecifications.putAll(getRadius());
        shapeSpecifications.putAll(getDiameter());

        return shapeSpecifications;
    }

    @Override
    public LinkedHashMap<String, String> getShapeName() {
        LinkedHashMap<String, String> shapeName = new LinkedHashMap<>();
        shapeName.put("Name", "Circle");

        return shapeName;
    }

    @Override
    public LinkedHashMap<String, String> getShapeArea() {
        LinkedHashMap<String, String> shapeArea = new LinkedHashMap<>();
        shapeArea.put("Area", String.format("%.2f", Math.PI * radius * radius));

        return shapeArea;
    }

    @Override
    public LinkedHashMap<String, String> getPerimeterOfTheFigure() {
        LinkedHashMap<String, String> perimeterOfTheFigure = new LinkedHashMap<>();
        perimeterOfTheFigure.put("Perimeter", String.format("%.2f", 2 * Math.PI * radius));

        return perimeterOfTheFigure;
    }

    public LinkedHashMap<String, String> getRadius() {
        LinkedHashMap<String, String> circleRadius = new LinkedHashMap<>();
        circleRadius.put("Radius", String.valueOf(radius));

        return circleRadius;
    }

    public LinkedHashMap<String, String> getDiameter() {
        LinkedHashMap<String, String> circleDiameter = new LinkedHashMap<>();
        circleDiameter.put("Diameter", String.valueOf(2 * radius));

        return circleDiameter;
    }
}