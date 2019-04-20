package ru.cft.focusstart.turlyun.Shapes;

import java.util.LinkedHashMap;

public class Circle extends SomeShape {
    private int radius;

    public Circle() {
        this.radius = 5;
    }

    public Circle(int[] radius) {
        this.radius = radius[0];
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
        LinkedHashMap<String, String> circleName = new LinkedHashMap<>();
        circleName.put("Name", "Circle");

        return circleName;
    }

    @Override
    public LinkedHashMap<String, String> getShapeArea() {
        LinkedHashMap<String, String> circleArea = new LinkedHashMap<>();
        circleArea.put("Area", String.format("%.2f", Math.PI * radius * radius));

        return circleArea;
    }

    @Override
    public LinkedHashMap<String, String> getPerimeterOfTheFigure() {
        LinkedHashMap<String, String> perimeterOfTheCircle = new LinkedHashMap<>();
        perimeterOfTheCircle.put("Perimeter", String.format("%.2f", 2 * Math.PI * radius));

        return perimeterOfTheCircle;
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