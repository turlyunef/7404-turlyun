package ru.cft.focusstart.turlyun;

import ru.cft.focusstart.turlyun.shapes.Shape;
import ru.cft.focusstart.turlyun.shapes.Circle;
import ru.cft.focusstart.turlyun.shapes.Rectangle;
import ru.cft.focusstart.turlyun.shapes.Triangle;

/**
 * The class is intended for generating a shape class instance by enum Figure.
 * The instance is created based on the shape parameters.
 */
public class ShapeGenerator {

    /**
     * Generates a shape class instance.
     *
     * @param inputShapeParameters an object containing parameters for creating a specifications of a shape
     * @return the instance of a shape class
     * @throws ShapeSpecificationsException Exception is generated if there is no type of figure in enum Figure
     */
    public static Shape generateShape(InputShapeParameters inputShapeParameters) throws ShapeSpecificationsException {
        if (Figure.CIRCLE.getName().equals(inputShapeParameters.getShapeName())) {
            checkInputShapeParameters(inputShapeParameters, Figure.CIRCLE);

            return new Circle(inputShapeParameters.getShapeParameters());
        } else if (Figure.TRIANGLE.getName().equals(inputShapeParameters.getShapeName())) {
            checkInputShapeParameters(inputShapeParameters, Figure.TRIANGLE);

            return new Triangle(inputShapeParameters.getShapeParameters());
        } else if (Figure.RECTANGLE.getName().equals(inputShapeParameters.getShapeName())) {
            checkInputShapeParameters(inputShapeParameters, Figure.RECTANGLE);

            return new Rectangle(inputShapeParameters.getShapeParameters());
        } else {
            throw new ShapeSpecificationsException(inputShapeParameters.getShapeName() +
                    " shape is not found in the enum Figure, check first row in the input file.");
        }
    }

    /**
     * Checks whether there are enough input parameters to generate an instance of a figure object.
     * Otherwise, throws an exception ShapeSpecificationsException.
     *
     * @param inputShapeParameters an object containing parameters for creating a specifications of a figure
     * @param figure                types of shapes used for entry
     * @throws ShapeSpecificationsException Exception is generated if the number of parameters is not enough
     *                                      to create a specifications of a figure
     */
    private static void checkInputShapeParameters(InputShapeParameters inputShapeParameters, Figure figure)
            throws ShapeSpecificationsException {
        if (inputShapeParameters.getShapeParameters().length < figure.getParametersCount()) {
            throw new ShapeSpecificationsException(inputShapeParameters.getShapeName() +
                    " shape and entered parameters is not compatible, check second row in the input file.");
        }
    }
}