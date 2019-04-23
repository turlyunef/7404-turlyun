package ru.cft.focusstart.turlyun.shapes;

import java.util.LinkedHashMap;

/**
 * The class to create the specification of a rectangle
 */
public class Rectangle extends SomeShape {
    private final int side1;
    private final int side2;

    /**
     * Constructor to create a rectangle instance based on its sides.
     *
     * @param numbers for a rectangle, the parameters contain only two sides for it
     */
    public Rectangle(int[] numbers) {
        this.side1 = numbers[0];
        this.side2 = numbers[1];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkedHashMap<String, String> getShapeSpecifications() {
        LinkedHashMap<String, String> shapeSpecifications = new LinkedHashMap<>(super.getShapeSpecifications());
        shapeSpecifications.putAll(getDiagonalLength());
        shapeSpecifications.putAll(getRectangleLength());
        shapeSpecifications.putAll(getRectangleWidth());

        return shapeSpecifications;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkedHashMap<String, String> getShapeName() {
        LinkedHashMap<String, String> shapeName = new LinkedHashMap<>();
        shapeName.put("Name", "Rectangle");

        return shapeName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkedHashMap<String, String> getShapeArea() {
        LinkedHashMap<String, String> shapeArea = new LinkedHashMap<>();
        shapeArea.put("Area", String.valueOf(side1 * side2));

        return shapeArea;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkedHashMap<String, String> getPerimeterOfTheFigure() {
        LinkedHashMap<String, String> perimeterOfTheFigure = new LinkedHashMap<>();
        perimeterOfTheFigure.put("Perimeter", String.valueOf(2 * side1 + 2 * side2));

        return perimeterOfTheFigure;
    }

    /**
     * Returns a LinkedHashMap with a rectangle diagonal length.
     *
     * @return a LinkedHashMap with a rectangle diagonal length
     */
    private LinkedHashMap<String, String> getDiagonalLength() {
        LinkedHashMap<String, String> rectangleDiagonalLength = new LinkedHashMap<>();
        rectangleDiagonalLength.put("Diagonal Length", String.format("%.2f", Math.sqrt(Math.pow(side1, 2) + Math.pow(side2, 2))));

        return rectangleDiagonalLength;
    }

    /**
     * Returns a LinkedHashMap with a rectangle length.
     *
     * @return a LinkedHashMap with a rectangle length
     */
    private LinkedHashMap<String, String> getRectangleLength() {
        LinkedHashMap<String, String> rectangleLength = new LinkedHashMap<>();
        if (side2 > side1) {
            rectangleLength.put("Rectangle Length", String.valueOf(side2));
        } else {
            rectangleLength.put("Rectangle Length", String.valueOf(side1));
        }
        return rectangleLength;
    }

    /**
     * Returns a LinkedHashMap with a rectangle width.
     *
     * @return a LinkedHashMap with a rectangle width
     */
    private LinkedHashMap<String, String> getRectangleWidth() {
        LinkedHashMap<String, String> rectangleWidth = new LinkedHashMap<>();
        if (side2 > side1) {
            rectangleWidth.put("Rectangle Width", String.valueOf(side1));
        } else {
            rectangleWidth.put("Rectangle Width", String.valueOf(side2));
        }

        return rectangleWidth;
    }
}