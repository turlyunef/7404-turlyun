package model.game.field.outside;

import model.game.field.Model;

/**
 * Interface level of abstraction, which displays the model of the game displaying the status of closed cells.
 * Monitors the correct location of flags by the user on the playing field.
 */
public interface OutsideModel extends Model {

    /**
     * Changes cell status: puts or removes the flag from the field
     *
     * @param rowIndex index on the rows of this cell
     * @param colIndex index on the columns of this cell
     * @return cell status
     */
    CellStatus changeCellStatus(int rowIndex, int colIndex);

    /**
     * Sets the cell status to open
     *
     * @param rowIndex index on the rows of this cell
     * @param colIndex index on the columns of this cell
     */
    void setOpen(int rowIndex, int colIndex);

    /**
     * Returns the number of flags set by the user on the playing field
     *
     * @return number of flags
     */
    int getFlagCount();
}