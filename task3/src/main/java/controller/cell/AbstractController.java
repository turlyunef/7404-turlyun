package controller.cell;

public abstract class AbstractController {
    CellStatus cellStatus;
    private int flaggedBombsCounter;

    AbstractController() {
        this.flaggedBombsCounter = 0;
        this.cellStatus = CellStatus.CLOSE;
    }

    public abstract void setExplodedMineCell();

    public abstract void setMineCell();

    public abstract void setFlag();

    public abstract void setClose();

    public abstract void setFalseMinedCell();

    public abstract void setOpenCell(int bombsAroundCellCount);

    void changeFlaggedBombsCounter(int value) {
        flaggedBombsCounter += value;
    }

    int getFlaggedBombsCounter() {

        return flaggedBombsCounter;
    }

    CellStatus getCellStatus() {

        return cellStatus;
    }
}