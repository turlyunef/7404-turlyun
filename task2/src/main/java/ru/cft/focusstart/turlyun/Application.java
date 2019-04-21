package ru.cft.focusstart.turlyun;

import ru.cft.focusstart.turlyun.shapes.Shape;

/**
 * The program for creating a specification of a shape according to its input parameters.
 * Parameters are read from the input file.
 * The name of the input file is passed in the arguments of the method main().
 * Also in the arguments, the second element can be passed the name of the output file in which the specification will be written.
 * If the output file is not specified, the specification is output to the console.
 * For each shape, a class with the same name must be configured.
 */
public class Application {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("You must enter at least one file name!");
        } else {
            try {
                ShapeParametersFromFile shapeParametersFromFile = InputFromFileOperator.getShapeParametersFromFile(args[0]);
                Shape shape = ShapeCreator.createShape(shapeParametersFromFile);

                if (args.length == 1) {
                    GeneratorOfSpec.generateShapeSpecifications(shape);
                } else {
                    GeneratorOfSpec.generateShapeSpecifications(shape, args[1]);
                }
            } catch (ShapeSpecificationsException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}