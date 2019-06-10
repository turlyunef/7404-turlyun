package model.game.field.cell;

/**
 * Cell of the playing field.
 */
public class Cell {
    private final CellContent content;
    private final int rowIndex;
    private final int colIndex;
    private CellStatus status = CellStatus.CLOSE;
    private int flaggedBombsCounter = 0;
    private int bombsAroundCellCount;

    /**
     * Cell creation constructor assigning its contents and coordinates.
     *
     * @param cellContent inner field content
     * @param rowIndex    index on the rows of the cell
     * @param colIndex    index on the columns of the cell
     */
    public Cell(CellContent cellContent, int rowIndex, int colIndex) {
        this.content = cellContent;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    /**
     * Gets index on the rows of the cell.
     *
     * @return row index of the cell
     */
    public int getRowIndex() {

        return this.rowIndex;
    }

    /**
     * Gets index on the columns of the cell.
     *
     * @return column index of the cell
     */
    public int getColIndex() {

        return this.colIndex;
    }

    /**
     * Returns inner cell content.
     *
     * @return cell content
     */
    public CellContent getContent() {

        return this.content;
    }

    /**
     * Returns the number of bombs around a given cell.
     *
     * @return number of bombs around a given cell
     */
    public int getBombsAroundCellCount() {

        return this.bombsAroundCellCount;
    }

    /**
     * Sets the number of bombs around a given cell.
     *
     * @param bombsAroundCellCount number of bombs around a given cell
     */
    public void setBombsAroundCellCount(int bombsAroundCellCount) {
        this.bombsAroundCellCount = bombsAroundCellCount;
    }

    /**
     * Returns user interaction cell status.
     *
     * @return status of the cell
     */
    public CellStatus getStatus() {

        return this.status;
    }

    /**
     * Sets user interaction cell status.
     *
     * @param status status of the cell
     */
    public void setStatus(CellStatus status) {
        this.status = status;
    }

    /**
     * Determines whether the given cell is a bomb.
     *
     * @return true if this field is a bomb else false
     */
    public boolean isBomb() {

        return this.content.equals(CellContent.BOMB);
    }

    /**
     * Returns the number of flags set in the cells around this cell.
     *
     * @return number of flags around this cell.
     */
    public int getFlaggedBombsCounter() {

        return this.flaggedBombsCounter;
    }

    /**
     * Changes the number of flags set in the field around this cell.
     *
     * @param value value to change the flag counter for this cell
     */
    public void changeFlaggedBombsCounter(int value) {
        this.flaggedBombsCounter += value;
    }
}