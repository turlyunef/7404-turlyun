package model.game.field;

import model.game.field.cell.Cell;
import model.game.field.cell.CellStatus;

import java.util.List;

/**
 * Playing field interface.
 */
public interface Field {

    /**
     * Returns a cell of the playing field.
     *
     * @param rowIndex index on the rows of this cell
     * @param colIndex index on the columns of this cell
     * @return cell
     */
    Cell getCell(int rowIndex, int colIndex);

    /**
     * Gets status of the cell.
     *
     * @param rowIndex index on the rows of this cell
     * @param colIndex index on the columns of this cell
     * @return cell status
     */
    CellStatus getCellStatus(int rowIndex, int colIndex);

    /**
     * Changes cell status: puts or removes the flag from the cell or opens cell.
     *
     * @param rowIndex   index on the rows of this cell
     * @param colIndex   index on the columns of this cell
     * @param cellStatus field status
     */
    void setCellStatus(int rowIndex, int colIndex, CellStatus cellStatus);

    /**
     * Returns a list of all cells with bombs. It does it if the game is over.
     *
     * @return list of all cells with bombs
     */
    List<Cell> getBombs();

    /**
     * Returns a list of all cells with flags.
     *
     * @return list of all cells with flags
     */
    List<Cell> getAllFlaggedCells();


//    void changeFlaggedBombsCountersInCellsAround(int rowIndex, int colIndex, int value);

    /**
     * Checks whether the number of flags set around a cell matches the value of the bombs around.
     *
     * @param rowIndex index on the rows of this cell
     * @param colIndex index on the columns of this cell
     * @return true if number of flags set around a cell matches the value of the bombs around, else false
     */
    boolean cellsAroundIsFlagged(int rowIndex, int colIndex);

    /**
     * Checks whether the cell with the given coordinates belongs to the playing field.
     *
     * @param rowIndex index on the rows of this cell
     * @param colIndex index on the columns of this cell
     * @return true if the cell belongs to the playing field, else false
     */
    boolean isCellExist(int rowIndex, int colIndex);
}