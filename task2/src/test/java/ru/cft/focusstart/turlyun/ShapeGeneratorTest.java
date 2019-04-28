package ru.cft.focusstart.turlyun;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.cft.focusstart.turlyun.shapes.Circle;
import ru.cft.focusstart.turlyun.shapes.Shape;
import ru.cft.focusstart.turlyun.shapes.Triangle;

import static org.junit.Assert.*;

public class ShapeGeneratorTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCreateShapeWithCircle() {
        try {
            InputShapeParameters inputShapeParameters = new InputShapeParameters();
            inputShapeParameters.setShapeName("Circle");
            int[] args = {8};
            inputShapeParameters.setShapeParameters(args);
            Shape actual = ShapeGenerator.createShape(inputShapeParameters);
            assertEquals(actual, new Circle((args)));
        } catch (ShapeSpecificationsException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReadSizeWithInvalidShapeName() throws ShapeSpecificationsException {
        InputShapeParameters inputShapeParameters = new InputShapeParameters();
        inputShapeParameters.setShapeName("Circ");
        int[] args = {8};
        inputShapeParameters.setShapeParameters(args);
        thrown.expect(ShapeSpecificationsException.class);
        thrown.expectMessage("Shape Circ is not found, check first row in the input file");
        Circle actual = (Circle) ShapeGenerator.createShape(inputShapeParameters);
    }

    @Test
    public void testReadSizeWithInvalidShapeParameters() throws ShapeSpecificationsException {
        InputShapeParameters inputShapeParameters = new InputShapeParameters();
        inputShapeParameters.setShapeName("Triangle");
        int[] args = {8};
        inputShapeParameters.setShapeParameters(args);
        thrown.expect(ShapeSpecificationsException.class);
        thrown.expectMessage("Shape Triangle and entered parameters is not compatible, check second row in the input file");
        Triangle actual = (Triangle) ShapeGenerator.createShape(inputShapeParameters);
    }
}