package ru.cft.focusstart.turlyun;

import java.io.*;

/**
 * The class is intended for reading and parsing data from a file.
 */
class InputReader {

    /**
     * Reads the shape parameters from the input file to the instance of the InputShapeParameters class
     *
     * @param fileName name of the file from which the shape parameters are initialized
     * @return an object of class InputShapeParameters,
     * which contains the necessary parameters for creating a specifications of a shape
     * @throws ShapeSpecificationsException Exception is thrown if the data cannot be read from the input file or
     *                                      if file has less than two lines.
     */
    static InputShapeParameters readShapeParametersFromFile(String fileName) throws ShapeSpecificationsException {
        InputShapeParameters inputShapeParameters = new InputShapeParameters();
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream))) {
            inputShapeParameters.setShapeName(bufferedReader.readLine());
            int[] shapeParameters = parseStringToIntArray(bufferedReader.readLine());
            inputShapeParameters.setShapeParameters(shapeParameters);

            return inputShapeParameters;
        } catch (FileNotFoundException e) {
            throw new ShapeSpecificationsException("The file " + fileName + " does not exist.");
        } catch (SecurityException e) {
            throw new ShapeSpecificationsException("Read access to the file " + fileName + " denied.");
        } catch (IOException e) {
            throw new ShapeSpecificationsException("The file " + fileName + " read error.");
        } catch (NullPointerException e) {
            throw new ShapeSpecificationsException("The file " + fileName + " must contain at least two lines.");
        }
    }

    /**
     * Splits a string by spaces into an array of numbers
     *
     * @param str string to split
     * @return array of integers derived from string
     * @throws ShapeSpecificationsException Exception is thrown if there are non-numeric characters in the string.
     */
    private static int[] parseStringToIntArray(String str) throws ShapeSpecificationsException {
        String[] splittedString = str.split(" ");
        int[] intArray = new int[splittedString.length];
        int i = 0;
        try {
            for (String x : splittedString) {
                intArray[i] = Integer.parseInt(x);
                i++;
            }

            return intArray;
        } catch (NumberFormatException e) {
            throw new ShapeSpecificationsException("Error reading shape parameters. " +
                    "Parameters in the second line of the input file must be numbers, separated by spaces.");
        }
    }
}