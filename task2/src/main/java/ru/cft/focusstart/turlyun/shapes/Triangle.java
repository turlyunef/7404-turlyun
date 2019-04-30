package ru.cft.focusstart.turlyun.shapes;

/**
 * The class to create the specifications of a triangle
 */
public class Triangle extends BaseShape {
    private final int side1;
    private final int side2;
    private final int side3;

    /**
     * Constructor to create a triangle instance and to generate specifications based on its sides.
     *
     * @param parameters parameters, for a triangle, contain only three sides for it
     */
    public Triangle(int[] parameters) {
        this.side1 = parameters[0];
        this.side2 = parameters[1];
        this.side3 = parameters[2];
        generateShapeSpecifications();
    }

    /**
     * {@inheritDoc}
     * , triangle lengths and angles
     */
    @Override
    protected void generateShapeSpecifications() {
        super.generateShapeSpecifications();
        appendTriangleLengthsAndAngles();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void appendShapeName() {
        appendShapeSpecifications("Name", "Triangle");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void appendShapeArea() {
        float semiPerimeter = (float) (side1 + side2 + side3) / 2;
        appendShapeSpecifications("Area", String.format("%.2f", Math.sqrt((semiPerimeter) *
                (semiPerimeter - side1) * (semiPerimeter - side2) * (semiPerimeter - side3))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void appendShapePerimeter() {
        appendShapeSpecifications("Perimeter", String.valueOf(side1 + side2 + side3));
    }

    /**
     * Generates a triangle lengths and angles and appends this to the specifications
     */
    private void appendTriangleLengthsAndAngles() {
        appendShapeSpecifications("First Length", String.valueOf(side1));
        appendShapeSpecifications("Angle opposite the first side", String.format("%.2f", countAngle(side2, side3, side1)));
        appendShapeSpecifications("Second Length", String.valueOf(side2));
        appendShapeSpecifications("Angle opposite the second side", String.format("%.2f", countAngle(side1, side3, side2)));
        appendShapeSpecifications("Third Length", String.valueOf(side3));
        appendShapeSpecifications("Angle opposite the third side", String.format("%.2f", countAngle(side1, side2, side3)));
    }

    /**
     * Calculates the angle of the triangle opposite to side3
     *
     * @param side1 the first side of triangle
     * @param side2 the second side of triangle
     * @param side3 the third side of triangle
     * @return the angle of the triangle opposite to side3
     */
    private double countAngle(int side1, int side2, int side3) {

        return Math.toDegrees(Math.acos((Math.pow(side1, 2) + Math.pow(side2, 2) - Math.pow(side3, 2)) / (2 * side1 * side2)));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {

            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {

            return false;
        }
        Triangle guest = (Triangle) obj;

        return (side1 == guest.side1) && (side2 == guest.side2) && (side3 == guest.side3);
    }
}