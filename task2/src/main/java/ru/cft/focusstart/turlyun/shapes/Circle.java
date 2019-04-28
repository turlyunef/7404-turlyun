package ru.cft.focusstart.turlyun.shapes;

/**
 * The class to create the specifications of a circle
 */
public class Circle extends BaseShape {
    private final int radius;

    /**
     * Constructor to create a circle instance and to generate specifications based on its radius.
     *
     * @param parameters for a circle, the parameters contain only a radius in the first element
     */
    public Circle(int[] parameters) {
        this.radius = parameters[0];
        generateShapeSpecifications();
    }

    /**
     * {@inheritDoc}
     * , radius, diameter
     */
    @Override
    protected void generateShapeSpecifications() {
        super.generateShapeSpecifications();
        appendRadius();
        appendDiameter();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void appendShapeName() {
        appendShapeSpecifications("Name", "Circle");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void appendShapeArea() {
        appendShapeSpecifications("Area", String.format("%.2f", Math.PI * radius * radius));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void appendShapePerimeter() {
        appendShapeSpecifications("Perimeter", String.format("%.2f", 2 * Math.PI * radius));
    }

    /**
     * Generates a radius of the shape and appends this to the specifications
     */
    protected void appendRadius() {
        appendShapeSpecifications("Radius", String.valueOf(radius));
    }

    /**
     * Generates a diameter of the shape and appends this to the specifications
     */
    protected void appendDiameter() {
        appendShapeSpecifications("Diameter", String.valueOf(2 * radius));
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