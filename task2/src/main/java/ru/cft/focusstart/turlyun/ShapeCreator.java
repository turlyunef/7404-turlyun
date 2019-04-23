package ru.cft.focusstart.turlyun;

import ru.cft.focusstart.turlyun.shapes.Shape;

import java.lang.reflect.InvocationTargetException;

/**
 * The class is intended for generating a shape class instance by reflection.
 * The search for a class corresponding to a type of shape is carried out by its name.
 * The instance is created based on the shape parameters.
 * Classes are searched for in the "ru.cft.focusstart.turlyun.shapes" package.
 * The characters of the name of the shape are corrected on CamelCase
 */
public class ShapeCreator {
    /**
     * Generates a shape class instance by reflection.
     *
     * @param shapeParametersFromFile an object containing parameters for creating a specification of a shape
     * @return the instance of shape class
     * @throws ShapeSpecificationsException Exception is generated if there is no class corresponding to the figure,
     *                                      or if the number of the figure parameters does not match its constructor
     */
    public static Shape createShape(ShapeParametersFromFile shapeParametersFromFile) throws ShapeSpecificationsException {
        Shape shapeClass;
        try {
            Class clazz = Class.forName("ru.cft.focusstart.turlyun.shapes." + changeCaseToCamelCase(shapeParametersFromFile.getShapeName()));
            shapeClass = (Shape) clazz.getDeclaredConstructor(int[].class).newInstance(shapeParametersFromFile.getShapeParameters());
            return shapeClass;
        } catch (ClassNotFoundException | NoSuchMethodException  e) {
            throw new ShapeSpecificationsException("Shape " + shapeParametersFromFile.getShapeName() + " is not found, check first row in the input file");
        }catch (InvocationTargetException | InstantiationException e){
            throw new ShapeSpecificationsException("Shape " + shapeParametersFromFile.getShapeName() + " and entered parameters is not compatible, check second row in the input file");
        }catch (IllegalAccessException e){
            throw new ShapeSpecificationsException("Class of the shape " + shapeParametersFromFile.getShapeName() + " has't access for creating its instance");
        }
    }

    /**
     * Align the string case to CamelCase
     *
     * @param str case-sensitive string
     * @return the string to which the register is aligned
     */
    private static String changeCaseToCamelCase(String str) {
        StringBuilder newStr = new StringBuilder();
        newStr.append(Character.toUpperCase(str.charAt(0)));
        for (int i = 1; i < str.length(); i++) {
            newStr.append(Character.toLowerCase(str.charAt(i)));
        }

        return String.valueOf(newStr);
    }
}