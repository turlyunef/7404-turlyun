package ru.cft.focusstart.turlyun;

import ru.cft.focusstart.turlyun.Shapes.Shape;

import java.util.Map;

public class Application {
    public static void main(String[] args) {
        int[] shapeParameters = {6};
        ShapeParametersFromFile shapeParametersFromFile = new ShapeParametersFromFile("Circle", shapeParameters);
        Shape circle = shapeParametersFromFile.getShape();
        if (circle != null) {
            for (Map.Entry entry : circle.getShapeSpecifications().entrySet()
            ) {
                System.out.printf("%s: %s\n", entry.getKey(), entry.getValue());
            }
        }
    }
}