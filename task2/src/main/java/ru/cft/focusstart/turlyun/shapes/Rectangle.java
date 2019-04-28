package ru.cft.focusstart.turlyun.shapes;

/**
 * The class to create the specifications of a rectangle
 */
public class Rectangle extends BaseShape {
    private final int side1;
    private final int side2;

    /**
     * Constructor to create a rectangle instance and to generate specifications based on its sides.
     *
     * @param parameters for a rectangle, the parameters contain only two sides for it
     */
    public Rectangle(int[] parameters) {
        this.side1 = parameters[0];
        this.side2 = parameters[1];
        generateShapeSpecifications();
    }

    /**
     * {@inheritDoc}
     * , length of the diagonal, rectangle length and rectangle width
     */
    @Override
    protected void generateShapeSpecifications() {
        super.generateShapeSpecifications();
        appendDiagonalLength();
        appendRectangleLength();
        appendRectangleWidth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void appendShapeName() {
        appendShapeSpecifications("Name", "Rectangle");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void appendShapeArea() {
        appendShapeSpecifications("Area", String.valueOf(side1 * side2));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void appendShapePerimeter() {
        appendShapeSpecifications("Perimeter", String.valueOf(2 * side1 + 2 * side2));
    }

    /**
     * Generates a diagonal length of the rectangle and appends this to the specifications
     */
    protected void appendDiagonalLength() {
        appendShapeSpecifications("Diagonal Length", String.format("%.2f", Math.sqrt(Math.pow(side1, 2) + Math.pow(side2, 2))));
    }

    /**
     * Generates a rectangle length and appends this to the specifications
     */
    protected void appendRectangleLength() {
        if (side2 > side1) {
            appendShapeSpecifications("Rectangle Length", String.valueOf(side2));
        } else {
            appendShapeSpecifications("Rectangle Length", String.valueOf(side1));
        }
    }

    /**
     * Generates a rectangle width and appends this to the specifications
     */
    protected void appendRectangleWidth() {
        if (side2 > side1) {
            appendShapeSpecifications("Rectangle Width", String.valueOf(side1));
        } else {
            appendShapeSpecifications("Rectangle Width", String.valueOf(side2));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Rectangle guest = (Rectangle) obj;
        return (side1 == guest.side1) && (side2 == guest.side2);
    }
}