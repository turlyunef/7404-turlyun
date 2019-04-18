package ru.cft.focusstart.turlyun;

import java.util.Map;

public class Application {
    public static void main(String[] args) {
        Shape circle = new Circle(5);
        for (Map.Entry entry : circle.getShapeSpecifications().entrySet()
             ) {
            System.out.printf("%s: %s\n", entry.getKey(), entry.getValue());
        }
    }
}
