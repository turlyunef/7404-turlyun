package ru.cft.focusstart.turlyun.Shapes;

import java.util.LinkedHashMap;

public class Triangle extends SomeShape {
    private int side1;
    private int side2;
    private int side3;

    public Triangle(int[] numbers) {
        this.side1 = numbers[0];
        this.side2 = numbers[1];
        this.side3 = numbers[2];
    }

    @Override
    public LinkedHashMap<String, String> getShapeSpecifications() {
        LinkedHashMap<String, String> shapeSpecifications = new LinkedHashMap<>(super.getShapeSpecifications());
        shapeSpecifications.putAll(getRectangleLengthsAndAngles());

        return shapeSpecifications;
    }

    @Override
    public LinkedHashMap<String, String> getShapeName() {
        LinkedHashMap<String, String> shapeName = new LinkedHashMap<>();
        shapeName.put("Name", "Triangle");

        return shapeName;
    }

    @Override
    public LinkedHashMap<String, String> getShapeArea() {
        LinkedHashMap<String, String> shapeArea = new LinkedHashMap<>();
        float semiPerimeter = (float) (side1 + side2 + side3) / 2;
        shapeArea.put("Area", String.format("%.2f", Math.sqrt((semiPerimeter) * (semiPerimeter - side1) * (semiPerimeter - side2) * (semiPerimeter - side3))));

        return shapeArea;
    }

    @Override
    public LinkedHashMap<String, String> getPerimeterOfTheFigure() {
        LinkedHashMap<String, String> perimeterOfTheFigure = new LinkedHashMap<>();
        perimeterOfTheFigure.put("Perimeter", String.valueOf(side1 + side2 + side3));

        return perimeterOfTheFigure;
    }

    public LinkedHashMap<String, String> getRectangleLengthsAndAngles() {
        LinkedHashMap<String, String> rectangleLengthsAndAngles = new LinkedHashMap<>();

        rectangleLengthsAndAngles.put("First Length", String.valueOf(side1));
        rectangleLengthsAndAngles.put("Angle opposite the first side", String.format("%.2f", countAngel(side2, side3, side1)));
        rectangleLengthsAndAngles.put("Second Length", String.valueOf(side2));
        rectangleLengthsAndAngles.put("Angle opposite the second side", String.format("%.2f", countAngel(side1, side3, side2)));
        rectangleLengthsAndAngles.put("Third Length", String.valueOf(side3));
        rectangleLengthsAndAngles.put("Angle opposite the third side", String.format("%.2f", countAngel(side1, side2, side3)));

        return rectangleLengthsAndAngles;
    }

    private double countAngel(int side1, int side2, int side3) {
        return Math.toDegrees(Math.acos((Math.pow(side1, 2) + Math.pow(side2, 2) - Math.pow(side3, 2)) / (2 * side1 * side2)));
    }
}