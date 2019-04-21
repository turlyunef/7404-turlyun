package ru.cft.focusstart.turlyun;

import java.io.*;

/**
 * The class is intended for reading data from a file.
 */
public class InputFromFileOperator {

    /**
     * Reads the shape parameters from the input file to an object of the ShapeParametersFromFile class
     *
     * @param fileName the name of the file from which the shape parameters are initialized
     * @return an object of class Shape Parameters From File, which contains the necessary parameters for creating a specification of a shape
     * @throws ShapeSpecificationsException Exception is thrown if the data cannot be read from the input file.
     */
    public static ShapeParametersFromFile getShapeParametersFromFile(String fileName) throws ShapeSpecificationsException {
        ShapeParametersFromFile shapeParametersFromFile = new ShapeParametersFromFile();
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));) {
            shapeParametersFromFile.setShapeName(bufferedReader.readLine());
            int[] shapeParameters = parseStringToIntArray(bufferedReader.readLine(), " ");
            shapeParametersFromFile.setShapeParameters(shapeParameters);
        } catch (FileNotFoundException e) {
            throw new ShapeSpecificationsException("File " + fileName + " does not exist.");
        } catch (SecurityException e) {
            throw new ShapeSpecificationsException("Read access to file " + fileName + " denied.");
        } catch (IOException e) {
            throw new ShapeSpecificationsException("File " + fileName + " read error.");
        }
        return shapeParametersFromFile;
    }

    /**
     * Splits a string by spaces into an array of numbers
     *
     * @param str       string to split
     * @param separator the separator by which the split line
     * @return array of integers derived from string
     * @throws ShapeSpecificationsException Exception is thrown if there are non-numeric characters in the string.
     */
    public static int[] parseStringToIntArray(String str, String separator) throws ShapeSpecificationsException {
        String[] splittedString = str.split(separator);
        int[] intArray = new int[splittedString.length];
        int i = 0;
        try {
            for (String x : splittedString) {
                intArray[i] = Integer.parseInt(x);
                i++;
            }

            return intArray;
        } catch (NumberFormatException e) {
            throw new ShapeSpecificationsException("Error reading shape parameters. Parameters in the second line of the input file must be numbers, separated by spaces");
        }
    }
}