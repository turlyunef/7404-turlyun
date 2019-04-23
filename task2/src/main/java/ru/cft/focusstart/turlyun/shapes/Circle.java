package ru.cft.focusstart.turlyun.shapes;

import java.util.LinkedHashMap;

/**
 * The class to create the specification of a circle
 */
public class Circle extends SomeShape {
    private final int radius;

    /**
     * Constructor to create a circle instance based on its radius.
     *
     * @param numbers for a circle, the parameters contain only a radius in the first element
     */
    public Circle(int[] numbers) {
        this.radius = numbers[0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkedHashMap<String, String> getShapeSpecifications() {
        LinkedHashMap<String, String> shapeSpecifications = new LinkedHashMap<>(super.getShapeSpecifications());
        shapeSpecifications.putAll(getRadius());
        shapeSpecifications.putAll(getDiameter());

        return shapeSpecifications;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkedHashMap<String, String> getShapeName() {
        LinkedHashMap<String, String> shapeName = new LinkedHashMap<>();
        shapeName.put("Name", "Circle");

        return shapeName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkedHashMap<String, String> getShapeArea() {
        LinkedHashMap<String, String> shapeArea = new LinkedHashMap<>();
        shapeArea.put("Area", String.format("%.2f", Math.PI * radius * radius));

        return shapeArea;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkedHashMap<String, String> getPerimeterOfTheFigure() {
        LinkedHashMap<String, String> perimeterOfTheFigure = new LinkedHashMap<>();
        perimeterOfTheFigure.put("Perimeter", String.format("%.2f", 2 * Math.PI * radius));

        return perimeterOfTheFigure;
    }

    /**
     * Returns a LinkedHashMap with a circle radius.
     *
     * @return a LinkedHashMap with a circle radius
     */
    private LinkedHashMap<String, String> getRadius() {
        LinkedHashMap<String, String> circleRadius = new LinkedHashMap<>();
        circleRadius.put("Radius", String.valueOf(radius));

        return circleRadius;
    }

    /**
     * Returns a LinkedHashMap with a circle diameter.
     *
     * @return a LinkedHashMap with a circle diameter
     */
    private LinkedHashMap<String, String> getDiameter() {
        LinkedHashMap<String, String> circleDiameter = new LinkedHashMap<>();
        circleDiameter.put("Diameter", String.valueOf(2 * radius));

        return circleDiameter;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Circle guest = (Circle) obj;
        return radius == guest.radius;
    }
}