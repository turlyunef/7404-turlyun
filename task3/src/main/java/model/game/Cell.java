package model.game;

public class Cell {
    private final CellContent cellContent;
    private int bombsAroundCellCount;

    public Cell(CellContent cellContent) {
        this.cellContent = cellContent;
    }

    public CellContent getCellContent() {

        return cellContent;
    }

    public int getBombsAroundCellCount() {

        return bombsAroundCellCount;
    }

    public void setBombsAroundCellCount(int bombsAroundCellCount) {
        this.bombsAroundCellCount = bombsAroundCellCount;
    }
}