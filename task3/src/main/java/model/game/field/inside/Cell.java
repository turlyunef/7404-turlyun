package model.game.field.inside;

/**
 * Cell of the playing field in the abstraction of the internal model of its contents.
 */
public class Cell {
    private final CellContent cellContent;
    private int bombsAroundCellCount;

    /**
     * Cell creation constructor assigning its contents.
     *
     * @param cellContent inner cell content
     */
    Cell(CellContent cellContent) {
        this.cellContent = cellContent;
    }

    /**
     * Returns inner cell content.
     *
     * @return cell content
     */
    public CellContent getCellContent() {

        return this.cellContent;
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
    void setBombsAroundCellCount(int bombsAroundCellCount) {
        this.bombsAroundCellCount = bombsAroundCellCount;
    }

    /**
     * Determines whether the given cell is a bomb.
     *
     * @return true if the given cell is a bomb
     */
    public boolean isCellBomb() {

        return this.cellContent.equals(CellContent.BOMB);
    }
}