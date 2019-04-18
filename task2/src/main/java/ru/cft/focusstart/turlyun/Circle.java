package ru.cft.focusstart.turlyun;

import java.util.LinkedHashMap;

public class Circle extends SomeShape  {
    private int radius;

    public Circle(int radius){
        this.radius = radius;
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
        circleName.put("Название", "Круг");
        return circleName;
    }

    @Override
    public LinkedHashMap<String, String> getShapeArea() {
        LinkedHashMap<String, String> circleArea = new LinkedHashMap<>();
        circleArea.put("Название", "Круг");
        return circleArea;
    }

    @Override
    public LinkedHashMap<String, String> getPerimeterOfTheFigure() {
        LinkedHashMap<String, String> perimeterOfTheCircle = new LinkedHashMap<>();
        perimeterOfTheCircle.put("Периметр", String.valueOf(2 * Math.PI * radius));
        return perimeterOfTheCircle;
    }

    public LinkedHashMap<String, String> getRadius(){
        LinkedHashMap<String, String> circleRadius = new LinkedHashMap<>();
        circleRadius.put("Радиус", String.valueOf(radius));

        return circleRadius;
    }

    public LinkedHashMap<String, String> getDiameter(){
        LinkedHashMap<String, String> circleDiameter = new LinkedHashMap<>();
        circleDiameter.put("Диаметр", String.valueOf(2 * radius));

        return circleDiameter;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
