package ru.cft.focusstart.turlyun;

import ru.cft.focusstart.turlyun.Shapes.Shape;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class GeneratorOfSpec {

    public static void generateShapeSpecifications(Shape shape) throws ShapeSpecificationsException {
        System.out.println(convertShapeSpecToStringBuilder(shape));
    }

    public static void generateShapeSpecifications(Shape shape, String outputFileName) throws ShapeSpecificationsException {
        writeStringBuilderToFile(convertShapeSpecToStringBuilder(shape), outputFileName);
        System.out.println("The specification of the shape was written to the file " + outputFileName + ".");
    }

    private static StringBuilder convertShapeSpecToStringBuilder(Shape shape) throws ShapeSpecificationsException {
        if (shape != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry entry : shape.getShapeSpecifications().entrySet()
            ) {
                String str = String.format("%s: %s\n", entry.getKey(), entry.getValue());
                stringBuilder.append(str);
            }
            return stringBuilder;
        } else {
            throw new ShapeSpecificationsException("Unable to generate a shape specification, because shape is null");
        }
    }

    private static void writeStringBuilderToFile(StringBuilder str, String fileName) throws ShapeSpecificationsException {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            writer.append(str);
            writer.flush();
        } catch (IOException ex) {
            throw new ShapeSpecificationsException("Unable to generate a shape specification, because shape is null");
        }
    }
}