package ru.cft.focusstart.turlyun;

import ru.cft.focusstart.turlyun.shapes.Circle;
import ru.cft.focusstart.turlyun.shapes.Rectangle;
import ru.cft.focusstart.turlyun.shapes.Shape;
import ru.cft.focusstart.turlyun.shapes.Triangle;

/**
 * The class is intended for generating a shape class instance by enum Shapes.
 * The instance is created based on the shape parameters.
 */
public class ShapeGenerator {

    /**
     * Generates a shape class instance.
     *
     * @param inputShapeParameters an object containing parameters for creating a specification of a shape
     * @return the instance of shape class
     * @throws ShapeSpecificationsException Exception is generated if there is no type of figure in enum Shapes and
     * if the number of parameters is not enough to create a specifications of a shape
     */
    public static Shape createShape(InputShapeParameters inputShapeParameters) throws ShapeSpecificationsException {
        try {
            if (Shapes.CIRCLE.getShapeName().equals(inputShapeParameters.getShapeName())) {
                return new Circle(inputShapeParameters.getShapeParameters());
            } else if (Shapes.TRIANGLE.getShapeName().equals(inputShapeParameters.getShapeName())) {
                return new Triangle(inputShapeParameters.getShapeParameters());
            } else if (Shapes.RECTANGLE.getShapeName().equals(inputShapeParameters.getShapeName())) {
                return new Rectangle(inputShapeParameters.getShapeParameters());
            } else {
                throw new ShapeSpecificationsException("Shape " + inputShapeParameters.getShapeName() +
                        " is not found in enum Shapes, check first row in the input file");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ShapeSpecificationsException("Shape " + inputShapeParameters.getShapeName() +
                    " and entered parameters is not compatible, check second row in the input file");
        }
    }
}