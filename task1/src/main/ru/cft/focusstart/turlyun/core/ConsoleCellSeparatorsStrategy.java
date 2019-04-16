package ru.cft.focusstart.turlyun.core;


/**
 * The strategy of creating elements of a table separators. The table is intended for output to the console.
 *
 * @see CellSeparatorStrategy
 */
public class ConsoleCellSeparatorsStrategy implements CellSeparatorStrategy {
    private static final String HORIZONTAL_ELEMENT_SEPARATOR = "-";
    private static final String VERTICAL_ELEMENT_SEPARATOR = "|";
    private static final String INTERSECTION_ELEMENT_SEPARATOR = "+";

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHorizontalElementOfCellSeparator() {

        return VERTICAL_ELEMENT_SEPARATOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVerticalElementOfCellSeparator() {

        return HORIZONTAL_ELEMENT_SEPARATOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIntersectionSeparatorElement() {

        return INTERSECTION_ELEMENT_SEPARATOR;
    }
}