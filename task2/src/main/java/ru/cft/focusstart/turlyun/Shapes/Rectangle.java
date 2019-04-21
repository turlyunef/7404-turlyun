package ru.cft.focusstart.turlyun.Shapes;

import java.util.LinkedHashMap;

public class Rectangle extends SomeShape {
    private int side1;
    private int side2;

    public Rectangle(int[] numbers) {
        this.side1 = numbers[0];
        this.side2 = numbers[1];
    }

    @Override
    public LinkedHashMap<String, String> getShapeSpecifications() {
        LinkedHashMap<String, String> shapeSpecifications = new LinkedHashMap<>(super.getShapeSpecifications());
        shapeSpecifications.putAll(getDiagonalLength());
        shapeSpecifications.putAll(getRectangleLength());
        shapeSpecifications.putAll(getRectangleWidth());

        return shapeSpecifications;
    }

    @Override
    public LinkedHashMap<String, String> getShapeName() {
        LinkedHashMap<String, String> shapeName = new LinkedHashMap<>();
        shapeName.put("Name", "Rectangle");

        return shapeName;
    }

    @Override
    public LinkedHashMap<String, String> getShapeArea() {
        LinkedHashMap<String, String> shapeArea = new LinkedHashMap<>();
        shapeArea.put("Area", String.valueOf(side1 * side2));

        return shapeArea;
    }

    @Override
    public LinkedHashMap<String, String> getPerimeterOfTheFigure() {
        LinkedHashMap<String, String> perimeterOfTheFigure = new LinkedHashMap<>();
        perimeterOfTheFigure.put("Perimeter", String.valueOf(2 * side1 + 2 * side2));

        return perimeterOfTheFigure;
    }

    public LinkedHashMap<String, String> getDiagonalLength() {
        LinkedHashMap<String, String> rectangleDiagonalLength = new LinkedHashMap<>();
        rectangleDiagonalLength.put("Diagonal Length", String.format("%.2f", Math.sqrt(Math.pow(side1, 2) + Math.pow(side2, 2))));

        return rectangleDiagonalLength;
    }

    public LinkedHashMap<String, String> getRectangleLength() {
        LinkedHashMap<String, String> rectangleLength = new LinkedHashMap<>();
        if (side2 > side1) {
            rectangleLength.put("Rectangle Length", String.valueOf(side2));
        } else {
            rectangleLength.put("Rectangle Length", String.valueOf(side1));
        }
        return rectangleLength;
    }

    public LinkedHashMap<String, String> getRectangleWidth() {
        LinkedHashMap<String, String> rectangleWidth = new LinkedHashMap<>();
        if (side2 > side1) {
            rectangleWidth.put("Rectangle Width", String.valueOf(side1));
        } else {
            rectangleWidth.put("Rectangle Width", String.valueOf(side2));
        }

        return rectangleWidth;
    }
}