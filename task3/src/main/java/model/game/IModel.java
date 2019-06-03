package model.game;

import model.game.field.cell.Cell;
import model.game.field.cell.CellStatus;

/**
 * Game model interface.
 */
public interface IModel {

    /**
     * Returns the state of the game: play, lose or win.
     *
     * @return state of the game
     */
    GameState getGameState();

    /**
     * Returns a cell of the playing field.
     *
     * @param rowIndex index on the rows of this cell
     * @param colIndex index on the columns of this cell
     * @return cell of the playing field
     */
    Cell getCell(int rowIndex, int colIndex);

    /**
     * Changes the status of a cell when a user performs actions with a cell.
     *
     * @param rowIndex index on the rows of this cell
     * @param colIndex index on the columns of this cell
     * @return status of a cell
     */
    CellStatus changeCellStatus(int rowIndex, int colIndex);

    /**
     * Returns the number of flags set by the user on the playing field
     *
     * @return number of flags
     */
    int getFlagCount();
}