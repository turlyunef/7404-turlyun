package ru.cft.focusstart.turlyun;

import ru.cft.focusstart.turlyun.shapes.Shape;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class SpecificationWriter {

    /**
     * Writes the shape specifications to the console.
     *
     * @param shape shape instance for console output
     */
    public static void writeShapeSpecifications(Shape shape) {
        System.out.println(generateShapeSpecification(shape));
    }

    /**
     * Writes the figure specification to the file.
     *
     * @param shape          shape object for console output
     * @param outputFileName the name of the file to which the shape specification will be written
     * @throws ShapeSpecificationsException Exception can be generated when converting a shape object to a StringBuilder
     */
    public static void writeShapeSpecifications(Shape shape, String outputFileName) throws ShapeSpecificationsException {
        writeStringBuilderToFile(generateShapeSpecification(shape), outputFileName);
        System.out.println("The specification of the shape was written to the file " + outputFileName + ".");
    }

    /**
     * Generates shape specification by the shape instance and converts it to a StringBuilder.
     *
     * @param shape a shape instance that needs to be converted to a StringBuilder
     * @return StringBuilder created from the shape instance
     */
    public static StringBuilder generateShapeSpecification(Shape shape) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry entry : shape.getShapeSpecifications().entrySet()
        ) {
            String str = String.format("%s: %s\n", entry.getKey(), entry.getValue());
            stringBuilder.append(str);
        }

        return stringBuilder;
    }

    /**
     * Writes a StringBuilder instance to a file.
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
        } catch (IOException ex) {
            throw new ShapeSpecificationsException("Cannot write information to file" + fileName + ".");
        }
    }
}