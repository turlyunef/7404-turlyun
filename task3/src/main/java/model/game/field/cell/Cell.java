package model.game.field.cell;

/**
 * Cell of the playing field.
 */
public class Cell {
    private final CellContent content;
    private CellStatus status = CellStatus.CLOSE;
    private int bombsAroundCellCount;
    private int flaggedBombsCounter = 0;
    private int rowIndex;
    private int colIndex;

    /**
     * Cell creation constructor assigning its contents.
     *
     * @param cellContent inner cell content
     * @param rowIndex
     * @param colIndex TODO
     */
    public Cell(CellContent cellContent, int rowIndex, int colIndex) {
        this.content = cellContent;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    /**
     * TODO
     * @return
     */
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     * TODO
     * @return
     */
    public int getColIndex() {
        return colIndex;
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
     * @return true if this cell is a bomb
     */
    public boolean isBomb() {

        return this.content.equals(CellContent.BOMB);
    }

    /**
     * Returns the number of flags set in the field around this cell.
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