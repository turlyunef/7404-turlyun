package model.game;

import model.game.field.cell.Cell;
import model.game.field.cell.CellStatus;
import model.game.statistic.Winner;

import java.util.List;

/**
 * Game model interface.
 */
public interface Model {

    /**
     * Restarts game field with new game properties.
     *
     * @param gameProperties game properties containing number of bombs of the game, number of rows and columns game field
     * @throws TableGenerationException when the number of bombs and the number of cells of the playing field are not compatible
     */
    void restartGameField(GameProperties gameProperties) throws TableGenerationException;

    /**
     * Returns the state of the game.
     *
     * @return state of the game
     */
    GameState getGameState();

    /**
     * Returns the list of opened cells when controller trying to open one closed cell.
     *
     * @param rowIndex index on the rows of opened cell
     * @param colIndex index on the columns of opened cell
     * @return list of opened cells
     */
    List<Cell> getOpenedCellsByOpenCell(int rowIndex, int colIndex);

    /**
     * Returns a list of cells to be opened, when trying to open cells around a specified cell that is already open.
     *
     * @param rowIndex index on the rows of the specified cell
     * @param colIndex index on the columns of the specified cell
     * @return list of opened cells
     */
    List<Cell> getOpenedCellsByOpenCellsAround(int rowIndex, int colIndex);

    /**
     * Changes the status of a field when a user performs actions with the specified cell.
     *
     * @param rowIndex index on the rows of the specified cell
     * @param colIndex index on the columns of the specified cell
     * @return status of the specified cell
     */
    CellStatus changeCellStatus(int rowIndex, int colIndex);

    /**
     * Returns the number of flags set by the user on the playing field.
     *
     * @return number of flags
     */
    int getFlagCount();

    /**
     * Returns a list of cells in which bombs are set.
     *
     * @return list of cells with bombs
     */
    List<Cell> getAllBombs();

    /**
     * Returns a list of cells in which flag are set.
     *
     * @return list of cells with flags
     */
    List<Cell> getFlaggedCells();

    /**
     * Returns list of all winners.
     *
     * @return list of all winners
     */
    List<Winner> getWinners();

    /**
     * Returns the current game time.
     *
     * @return current game time
     */
    float getGameTime();
}