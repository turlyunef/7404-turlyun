package ru.cft.focusstart.turlyun;

import ru.cft.focusstart.turlyun.shapes.Shape;

/**
 * The program for creating a specifications of a shape according to its input parameters.
 * Parameters are read from the input file.
 * The name of the input file is passed in the arguments of the method main().
 * Also in the arguments, the second element can be passed the name of the output file,
 * in which the specification will be written.
 * If the output file is not specified, the specifications is output to the console.
 */
public class Application {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("You must enter at least one file name!");
        } else {
            try {
                InputShapeParameters inputShapeParameters = InputReader.readShapeParametersFromFile(args[0]);
                Shape shape = ShapeGenerator.generateShape(inputShapeParameters);
                if (args.length == 1) {
                    SpecificationWriter.writeShapeSpecifications(shape);
                } else {
                    SpecificationWriter.writeShapeSpecifications(shape, args[1]);
                }
            } catch (ShapeSpecificationsException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}