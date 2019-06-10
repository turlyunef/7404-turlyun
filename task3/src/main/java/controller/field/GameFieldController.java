package controller.field;

import view.CellButton;

/**
 * Game board interface consisting all controllers.
 */
public interface GameFieldController {

    /**
     * Tries to open a cell in the playing field.
     *
     * @param rowIndex index on the rows of the cell
     * @param colIndex index on the columns of the cell
     */
    void tryOpenCell(int rowIndex, int colIndex);

    /**
     * Opens cells around a given cell.
     *
     * @param rowIndex index on the rows of the cell
     * @param colIndex index on the columns of the cell
     */
    void openCellsAround(int rowIndex, int colIndex);

    /**
     * Changes the status of a cell depending on the user's actions.
     *
     * @param rowIndex index on the rows of the cell
     * @param colIndex index on the columns of the cell
     */
    void changeCellStatus(int rowIndex, int colIndex);

    /**
     * Sets the controller for the specified cell.
     *
     * @param cellButton controller for the specified cell
     * @param rowIndex   index on the rows of this cell
     * @param colIndex   index on the columns of this cell
     */
    void setController(CellButton cellButton, int rowIndex, int colIndex);
}