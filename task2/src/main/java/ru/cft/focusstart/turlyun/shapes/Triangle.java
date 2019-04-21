package ru.cft.focusstart.turlyun.shapes;

import java.util.LinkedHashMap;

/**
 * The class to create the specification of a triangle
 */
public class Triangle extends SomeShape {
    private int side1;
    private int side2;
    private int side3;

    /**
     * Constructor to create a triangle instance based on its sides.
     *
     * @param numbers for a triangle, the parameters contain only three sides for it
     */
    public Triangle(int[] numbers) {
        this.side1 = numbers[0];
        this.side2 = numbers[1];
        this.side3 = numbers[2];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkedHashMap<String, String> getShapeSpecifications() {
        LinkedHashMap<String, String> shapeSpecifications = new LinkedHashMap<>(super.getShapeSpecifications());
        shapeSpecifications.putAll(getTriangleLengthsAndAngles());

        return shapeSpecifications;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkedHashMap<String, String> getShapeName() {
        LinkedHashMap<String, String> shapeName = new LinkedHashMap<>();
        shapeName.put("Name", "Triangle");

        return shapeName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkedHashMap<String, String> getShapeArea() {
        LinkedHashMap<String, String> shapeArea = new LinkedHashMap<>();
        float semiPerimeter = (float) (side1 + side2 + side3) / 2;
        shapeArea.put("Area", String.format("%.2f", Math.sqrt((semiPerimeter) * (semiPerimeter - side1) * (semiPerimeter - side2) * (semiPerimeter - side3))));

        return shapeArea;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkedHashMap<String, String> getPerimeterOfTheFigure() {
        LinkedHashMap<String, String> perimeterOfTheFigure = new LinkedHashMap<>();
        perimeterOfTheFigure.put("Perimeter", String.valueOf(side1 + side2 + side3));

        return perimeterOfTheFigure;
    }

    /**
     * Returns a LinkedHashMap with a triangle lengths and angles.
     *
     * @return a LinkedHashMap with a triangle lengths and angles
     */
    public LinkedHashMap<String, String> getTriangleLengthsAndAngles() {
        LinkedHashMap<String, String> triangleLengthsAndAngles = new LinkedHashMap<>();
        triangleLengthsAndAngles.put("First Length", String.valueOf(side1));
        triangleLengthsAndAngles.put("Angle opposite the first side", String.format("%.2f", countAngel(side2, side3, side1)));
        triangleLengthsAndAngles.put("Second Length", String.valueOf(side2));
        triangleLengthsAndAngles.put("Angle opposite the second side", String.format("%.2f", countAngel(side1, side3, side2)));
        triangleLengthsAndAngles.put("Third Length", String.valueOf(side3));
        triangleLengthsAndAngles.put("Angle opposite the third side", String.format("%.2f", countAngel(side1, side2, side3)));

        return triangleLengthsAndAngles;
    }

    /**
     * Calculates the angle of the triangle opposite to side3
     *
     * @param side1 the first side of triangle
     * @param side2 the second side of triangle
     * @param side3 the third side of triangle
     * @return the angle of the triangle opposite to side3
     */
    private double countAngel(int side1, int side2, int side3) {

        return Math.toDegrees(Math.acos((Math.pow(side1, 2) + Math.pow(side2, 2) - Math.pow(side3, 2)) / (2 * side1 * side2)));
    }
}