package ru.cft.focusstart.turlyun.shapes;

import java.util.Map;

/**
 * The root interface in the <i>shape hierarchy</i>.
 * For specifications information is stored in Map.
 * The key contains the name of the shape parameter, the value contains its value.
 */
public interface Shape {
    Map<String, String> getShapeSpecifications();
}