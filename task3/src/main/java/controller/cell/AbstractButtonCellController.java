package controller.cell;

import model.game.field.cell.CellStatus;

/**
 * Abstract class of the controller of the button cell of the playing field.
 */
public abstract class AbstractButtonCellController {
    CellStatus cellStatus;
    private int flaggedBombsCounter;

    /**
     * A constructor that creates a button with the state of a closed cell and the number of cleared bombs around the cell, equal to zero.
     */
    AbstractButtonCellController() {
        this.flaggedBombsCounter = 0;
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
     * Changes the number of flags set in the field around this cell.
     *
     * @param value value to change the flag counter for this cell
     */
    void changeFlaggedBombsCounter(int value) {
        this.flaggedBombsCounter += value;
    }

    /**
     * Returns the number of flags set in the field around this cell.
     *
     * @return number of flags around this cell.
     */
    int getFlaggedBombsCounter() {

        return this.flaggedBombsCounter;
    }

    /**
     * Returns the cell status.
     *
     * @return cell status
     */
    CellStatus getCellStatus() {

        return this.cellStatus;
    }
}