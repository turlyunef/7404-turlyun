package ru.cft.focusstart.turlyun;

import ru.cft.focusstart.turlyun.Shapes.Shape;

import java.lang.reflect.InvocationTargetException;

public class ShapeParametersFromFile {
    private String shapeName;
    private int[] shapeParameters;

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

    public Shape getShape() {
        Shape myClass = null;
        try {
            Class clazz = Class.forName("ru.cft.focusstart.turlyun.Shapes." + this.shapeName);
            myClass = (Shape) clazz.getDeclaredConstructor(int[].class).newInstance(shapeParameters);
            return myClass;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            System.out.printf("Shape %s is not found, check first row in the input file/n", this.shapeName);
            return null;
        } catch (IllegalArgumentException e) {
            System.out.printf("Shape %s and entered parameters is not compatible\n", this.shapeName);
            return null;
        }
    }
}