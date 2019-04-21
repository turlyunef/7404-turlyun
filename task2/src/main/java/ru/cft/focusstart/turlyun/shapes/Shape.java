package ru.cft.focusstart.turlyun.shapes;

import java.util.LinkedHashMap;

/**
 * The root interface in the <i>shape hierarchy</i>.
 * For specification information is stored in LinkedHashMap.
 * The key contains the name of the shape parameter, the value contains its value.
 */
public interface Shape {
    LinkedHashMap<String, String> getShapeSpecifications();
}