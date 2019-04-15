package ru.cft.focusstart.turlyun.core;

import ru.cft.focusstart.turlyun.SeparatorElements;

/**
 * The strategy of creating elements of a table separators. The table is intended for output to the console.
 *
 * @see CellSeparatorStrategy
 */
public class ConsoleCellSeparatorsStrategy implements CellSeparatorStrategy {
    private final String horizontalSeparatorElement = SeparatorElements.HORIZONTAL.getValue();
    private final String verticalSeparatorElement = SeparatorElements.VERTICAL.getValue();
    private final String intersectionSeparatorElement = SeparatorElements.INTERSECTION.getValue();

    /**
     * Separator element values are set through this constructor.
     */

    @Override
    public String getHorizontalElementOfCellSeparator() {

        return this.verticalSeparatorElement;
    }

    @Override
    public String getVerticalElementOfCellSeparator() {

        return this.horizontalSeparatorElement;
    }

    @Override
    public String getIntersectionSeparatorElement() {

        return this.intersectionSeparatorElement;
    }
}