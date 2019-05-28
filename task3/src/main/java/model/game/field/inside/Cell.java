package model.game.field.inside;

public class Cell {
    private final CellContent cellContent;
    private int bombsAroundCellCount;

    Cell(CellContent cellContent) {
        this.cellContent = cellContent;
    }

    public CellContent getCellContent() {

        return this.cellContent;
    }

    public int getBombsAroundCellCount() {

        return this.bombsAroundCellCount;
    }

    public boolean isCellBomb() {

        return this.cellContent.equals(CellContent.BOMB);
    }

    void setBombsAroundCellCount(int bombsAroundCellCount) {
        this.bombsAroundCellCount = bombsAroundCellCount;
    }
}