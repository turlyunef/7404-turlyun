package model.game;

public class Cell {
    private final CellContent cellContent;

    private int bombsAroundCellCount;

    Cell(CellContent cellContent) {
        this.cellContent = cellContent;
    }

    CellContent getCellContent() {

        return cellContent;
    }

    public int getBombsAroundCellCount() {

        return bombsAroundCellCount;
    }

    void setBombsAroundCellCount(int bombsAroundCellCount) {
        this.bombsAroundCellCount = bombsAroundCellCount;
    }
}