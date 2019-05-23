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

    public boolean isCellBomb() {
        return cellContent.equals(CellContent.BOMB);
    }

    void setBombsAroundCellCount(int bombsAroundCellCount) {
        this.bombsAroundCellCount = bombsAroundCellCount;
    }
}