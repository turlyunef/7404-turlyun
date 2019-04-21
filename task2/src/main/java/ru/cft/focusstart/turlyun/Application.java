package ru.cft.focusstart.turlyun;

import ru.cft.focusstart.turlyun.Shapes.Shape;

public class Application {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("You must enter at least one file name!");
        } else {
            try {
                ShapeParametersFromFile shapeParametersFromFile = InputFromFileOperator.getShapeParametersFromFile(args[0]);
                Shape shape = shapeParametersFromFile.getShape();

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