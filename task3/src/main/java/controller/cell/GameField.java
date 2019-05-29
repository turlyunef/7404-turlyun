package controller.cell;

import model.game.GameState;

/**
 * Game board interface consisting of cell controllers.
 */
public interface GameField {

    /**
     * Tries to open a cell.
     *
     * @param rowIndex index on the rows of the cell
     * @param colIndex index on the columns of the cell
     */
    void tryOpenCell(int rowIndex, int colIndex);

    /**
     * Sets the controller for the specified cell.
     *
     * @param buttonCellController controller for the specified cell
     * @param rowIndex             index on the rows of this cell
     * @param colIndex             index on the columns of this cell
     */
    void setController(ButtonCellController buttonCellController, int rowIndex, int colIndex);

    /**
     * Opens cells around a given.
     *
     * @param rowIndex index on the rows of the cell
     * @param colIndex index on the columns of the cell
     */
    void openCellsAround(int rowIndex, int colIndex);

    /**
     * Changes the status of a closed cell depending on the user's actions.
     *
     * @param rowIndex index on the rows of the cell
     * @param colIndex index on the columns of the cell
     */
    void changeCellStatus(int rowIndex, int colIndex);

    /**
     * Returns game status.
     *
     * @return game status
     */
    GameState getGameState();
}