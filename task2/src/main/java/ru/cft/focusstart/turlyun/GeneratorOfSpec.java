package ru.cft.focusstart.turlyun;

import ru.cft.focusstart.turlyun.shapes.Shape;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class GeneratorOfSpec {

    /**
     * Displays the figure specification in the console.
     *
     * @param shape shape object for console output
     * @throws ShapeSpecificationsException Exception can be generated when converting a shape object to a StringBuilder
     */
    public static void generateShapeSpecifications(Shape shape) throws ShapeSpecificationsException {
        System.out.println(convertShapeSpecToStringBuilder(shape));
    }

    /**
     * Write the figure specification to the file.
     *
     * @param shape          shape object for console output
     * @param outputFileName the name of the file to which the shape specification will be written
     * @throws ShapeSpecificationsException Exception can be generated when converting a shape object to a StringBuilder
     */
    public static void generateShapeSpecifications(Shape shape, String outputFileName) throws ShapeSpecificationsException {
        writeStringBuilderToFile(convertShapeSpecToStringBuilder(shape), outputFileName);
        System.out.println("The specification of the shape was written to the file " + outputFileName + ".");
    }

    /**
     * Converts a shape object to a StringBuilder.
     *
     * @param shape a shape object that needs to be converted to a StringBuilder
     * @return StringBuilder created from the shape object
     * @throws ShapeSpecificationsException An exception is thrown if the shape object is empty.
     */
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

    /**
     * Writes a StringBuilder to a file.
     *
     * @param str      StringBuilder to write to file
     * @param fileName the name of the file to which to write the StringBuilder
     * @throws ShapeSpecificationsException if the named file exists but is a directory rather
     *                                      than a regular file, does not exist but cannot be
     *                                      created, or cannot be opened for any other reason
     */
    private static void writeStringBuilderToFile(StringBuilder str, String fileName) throws ShapeSpecificationsException {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            writer.append(str);
            writer.flush();
        } catch (IOException ex) {
            throw new ShapeSpecificationsException("Cannot write information to file" + fileName);
        }
    }
}