package model.game.field;

import model.game.field.cell.Cell;
import model.game.field.cell.CellStatus;

/**
 * Playing field interface.
 */
public interface IField {

    /**
     * Returns a cell of the playing field
     *
     * @param rowIndex index on the rows of this cell
     * @param colIndex index on the columns of this cell
     * @return cell
     */
    Cell openCell(int rowIndex, int colIndex);

    /**
     * Gets status of the cell.
     *
     * @param rowIndex index on the rows of this cell
     * @param colIndex index on the columns of this cell
     * @return cell status
     */
    CellStatus getCellStatus(int rowIndex, int colIndex);

    /**
     * Changes cell status: puts or removes the flag from the field or opens cell.
     *
     * @param rowIndex   index on the rows of this cell
     * @param colIndex   index on the columns of this cell
     * @param cellStatus cell status
     */
    void setCellStatus(int rowIndex, int colIndex, CellStatus cellStatus);
}