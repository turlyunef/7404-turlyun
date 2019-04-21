package ru.cft.focusstart.turlyun;

import ru.cft.focusstart.turlyun.Shapes.Shape;

import java.lang.reflect.InvocationTargetException;

public class ShapeParametersFromFile {
    private String shapeName;
    private int[] shapeParameters;

    public ShapeParametersFromFile() {
        this.shapeName = null;
        this.shapeParameters = null;
    }

    public ShapeParametersFromFile(String shapeName, int[] shapeParameters) {
        this.shapeName = shapeName;
        this.shapeParameters = shapeParameters;
    }

    public String getShapeName() {
        return shapeName;
    }

    public int[] getShapeParameters() {
        return shapeParameters;
    }

    public void setShapeName(String shapeName) {
        this.shapeName = shapeName;
    }

    public void setShapeParameters(int[] shapeParameters) {
        this.shapeParameters = shapeParameters;
    }

    public Shape getShape() throws ShapeSpecificationsException {
        Shape myClass = null;
        try {
            Class clazz = Class.forName("ru.cft.focusstart.turlyun.Shapes." + changeCaseToCamelCase(this.shapeName));
            myClass = (Shape) clazz.getDeclaredConstructor(int[].class).newInstance(shapeParameters);
            return myClass;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new ShapeSpecificationsException("Shape " + this.shapeName + " is not found, check first row in the input file\n");
        } catch (IllegalArgumentException e) {
            throw new ShapeSpecificationsException("Shape " + this.shapeName + "and entered parameters is not compatible\n");
        }
    }

    private String changeCaseToCamelCase(String str) {
        StringBuilder newStr = new StringBuilder();
        newStr.append(Character.toUpperCase(str.charAt(0)));
        for (int i = 1; i < str.length(); i++) {
            newStr.append(Character.toLowerCase(str.charAt(i)));
        }

        return String.valueOf(newStr);
    }
}