package controller.cell;

import model.game.field.cell.CellStatus;

/**
 * The class of the controller of the button cell of the playing field.
 */
public abstract class AbstractButtonCellController {
    CellStatus cellStatus;


    /**
     * A constructor that creates a button with the state of a closed cell and the number of cleared bombs around the cell, equal to zero.
     */
    AbstractButtonCellController() {
        this.cellStatus = CellStatus.CLOSE;
    }

    /**
     * Explodes a mine in the this cell.
     */
    public abstract void setExplodedMineCell();

    /**
     * Shows mine in this cell.
     */
    public abstract void setMineCell();

    /**
     * Places flag on this cell.
     */
    public abstract void setFlag();

    /**
     * Sets this cell status of the closed cell.
     */
    public abstract void setClosed();

    /**
     * Shows on this cell that it is not a bomb and that the user mistakenly placed a flag on this cell.
     */
    public abstract void setFalseMinedCell();

    /**
     * Sets this cell status open.
     *
     * @param bombsAroundCellCount number of bombs around cell.
     */
    public abstract void setOpenCell(int bombsAroundCellCount);

    /**
     * Returns the cell status.
     *
     * @return cell status
     */
    CellStatus getCellStatus() {

        return this.cellStatus;
    }
}