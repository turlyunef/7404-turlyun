package controller;

abstract class AbstractController {
    CellStatus cellStatus;
    private int flaggedBombsCounter;

    AbstractController() {
        this.flaggedBombsCounter = 0;
        this.cellStatus = CellStatus.CLOSE;
    }

    abstract void setExplodedMineCell();

    abstract void setMineCell();

    abstract void setFlag();

    abstract void setClose();

    abstract void setFalseMinedCell();

    abstract void setOpenCell(int bombsAroundCellCount);

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