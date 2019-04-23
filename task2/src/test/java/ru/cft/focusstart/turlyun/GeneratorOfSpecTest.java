package ru.cft.focusstart.turlyun;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.cft.focusstart.turlyun.shapes.Circle;
import ru.cft.focusstart.turlyun.shapes.Rectangle;
import ru.cft.focusstart.turlyun.shapes.Shape;
import ru.cft.focusstart.turlyun.shapes.Triangle;

import static org.junit.Assert.*;

public class GeneratorOfSpecTest {
    @Test
    public void testConvertShapeSpecToStringBuilderWithCircle() throws ShapeSpecificationsException {
        int[] args = {8};
        Shape circle = new Circle(args);
        String actual = GeneratorOfSpec.convertShapeSpecToStringBuilder(circle).toString();
        String expected = "Name: Circle\n" +
                "Area: 201,06\n" +
                "Perimeter: 50,27\n" +
                "Radius: 8\n" +
                "Diameter: 16\n";
        assertEquals(expected, actual);
    }
    @Test
    public void testConvertShapeSpecToStringBuilderWithRectangle() throws ShapeSpecificationsException {
        int[] args = {8, 2};
        Shape rectangle = new Rectangle(args);
        String actual = GeneratorOfSpec.convertShapeSpecToStringBuilder(rectangle).toString();
        String expected = "Name: Rectangle\n" +
                "Area: 16\n" +
                "Perimeter: 20\n" +
                "Diagonal Length: 8,25\n" +
                "Rectangle Length: 8\n" +
                "Rectangle Width: 2\n";
        assertEquals(expected, actual);
    }

    @Test
    public void testConvertShapeSpecToStringBuilderWithTriangle() throws ShapeSpecificationsException {
        int[] args = {3, 2, 2};
        Shape triangle = new Triangle(args);
        String actual = GeneratorOfSpec.convertShapeSpecToStringBuilder(triangle).toString();
        String expected = "Name: Triangle\n" +
                "Area: 1,98\n" +
                "Perimeter: 7\n" +
                "First Length: 3\n" +
                "Angle opposite the first side: 97,18\n" +
                "Second Length: 2\n" +
                "Angle opposite the second side: 41,41\n" +
                "Third Length: 2\n" +
                "Angle opposite the third side: 41,41\n";
        assertEquals(expected, actual);
    }

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void testConvertShapeSpecToStringBuilderWithInvalidShapeName() throws ShapeSpecificationsException {
        Shape circle = null;
        thrown.expect(ShapeSpecificationsException.class);
        thrown.expectMessage("Unable to generate a shape specification, because shape is null");
        String actual = GeneratorOfSpec.convertShapeSpecToStringBuilder(circle).toString();
    }
}