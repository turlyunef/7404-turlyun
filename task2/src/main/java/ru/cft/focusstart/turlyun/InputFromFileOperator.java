package ru.cft.focusstart.turlyun;

import java.io.*;

public class InputFromFileOperator {
    public static ShapeParametersFromFile getShapeParametersFromFile(String fileName) throws ShapeSpecificationsException {
        ShapeParametersFromFile shapeParametersFromFile = new ShapeParametersFromFile();
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));) {
            shapeParametersFromFile.setShapeName(bufferedReader.readLine());
            int[] shapeParameters = parseStringToIntArray(bufferedReader.readLine());
            shapeParametersFromFile.setShapeParameters(shapeParameters);
        }
        catch (FileNotFoundException e) {
            throw new ShapeSpecificationsException("File " + fileName + " does not exist.");
        }
        catch (SecurityException e){
            throw new ShapeSpecificationsException("Read access to file " + fileName + " denied.");
        } catch (IOException e) {
            throw new ShapeSpecificationsException("File " + fileName + " read error.");
        }
        return shapeParametersFromFile;
    }

    public static int[] parseStringToIntArray(String str) throws ShapeSpecificationsException {
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
            throw new ShapeSpecificationsException("Error reading shape parameters. Parameters in the second line of the input file must be numbers, separated by spaces");
        }
    }
}
