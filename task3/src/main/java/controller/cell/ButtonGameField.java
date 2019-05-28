package controller.cell;

import model.game.GameState;
import model.game.MainModel;
import model.game.field.outside.CellStatus;

public class ButtonGameField implements GameField {

    private final MainModel gameMainModel;
    private final AbstractButtonCellController[][] cellControllers;

    public ButtonGameField(MainModel gameMainModel, AbstractButtonCellController[][] cellControllers) {
        this.gameMainModel = gameMainModel;
        this.cellControllers = cellControllers;
    }

    @Override
    public void tryOpenCell(int rowIndex, int columnIndex) {
        if (checkGameNotEnded() && (cellIsClose(rowIndex, columnIndex))) {
            if (this.gameMainModel.getCell(rowIndex, columnIndex).isCellBomb()) {
                explode(rowIndex, columnIndex);
            } else {
                int bombsAroundCellCount = this.gameMainModel.getCell(rowIndex, columnIndex).getBombsAroundCellCount();
                this.cellControllers[rowIndex][columnIndex].setOpenCell(bombsAroundCellCount);
                if (bombsAroundCellCount == 0) {
                    openCellsAround(rowIndex, columnIndex);
                }
            }
        }
    }

    @Override
    public void changeCellStatus(int rowIndex, int colIndex) {
        switch (this.gameMainModel.changeCellStatus(rowIndex, colIndex)) {
            case CLOSE: {
                changeDefusedBombsCountersInCellsAround(rowIndex, colIndex, -1);
                this.cellControllers[rowIndex][colIndex].setClosed();
                break;
            }
            case FLAG: {
                changeDefusedBombsCountersInCellsAround(rowIndex, colIndex, 1);
                this.cellControllers[rowIndex][colIndex].setFlag();
                break;
            }
        }
    }

    @Override
    public GameState getGameState() {

        return this.gameMainModel.getGameState();
    }

    @Override
    public void openCellsAround(int rowIndex, int colIndex) {
        if ((!cellIsClose(rowIndex, colIndex)) && (cellsAroundIsDemined(rowIndex, colIndex))) {
            for (int i = rowIndex - 1; i <= rowIndex + 1; i++) {
                for (int j = colIndex - 1; j <= colIndex + 1; j++) {
                    if (isCellExist(i, j)) {
                        tryOpenCell(i, j);
                    }
                }
            }
        }
    }

    @Override
    public void setController(ButtonCellController buttonCellController, int rowIndex, int colIndex) {
        this.cellControllers[rowIndex][colIndex] = buttonCellController;
    }

    private boolean checkGameNotEnded() {

        return (this.gameMainModel.getGameState().equals(GameState.PLAY));
    }

    private void explode(int rowIndex, int colIndex) {
        showAllBombs();
        this.cellControllers[rowIndex][colIndex].setExplodedMineCell();
    }

    private boolean cellIsClose(int rowIndex, int colIndex) {

        return this.cellControllers[rowIndex][colIndex].getCellStatus().equals(CellStatus.CLOSE);
    }

    private void showAllBombs() {
        for (int i = 0; i < this.cellControllers.length; i++) {
            for (int j = 0; j < this.cellControllers[i].length; j++) {
                if (this.gameMainModel.getCell(i, j).isCellBomb()) {
                    if (this.cellControllers[i][j].getCellStatus().equals(CellStatus.CLOSE)) {
                        this.cellControllers[i][j].setMineCell();
                    }
                } else {
                    if (this.cellControllers[i][j].getCellStatus().equals(CellStatus.FLAG)) {
                        this.cellControllers[i][j].setFalseMinedCell();
                    }
                }
            }
        }
    }

    private void changeDefusedBombsCountersInCellsAround(int rowIndex, int colIndex, int value) {
        for (int i = rowIndex - 1; i <= rowIndex + 1; i++) {
            for (int j = colIndex - 1; j <= colIndex + 1; j++) {
                if (isCellExist(i, j)) {
                    this.cellControllers[i][j].changeFlaggedBombsCounter(value);
                }
            }
        }
    }

    private boolean isCellExist(int rowIndex, int colIndex) {

        return !((rowIndex < 0) | (colIndex < 0) |
                (rowIndex >= this.cellControllers.length) |
                (colIndex >= this.cellControllers[0].length));
    }

    private boolean cellsAroundIsDemined(int rowIndex, int colIndex) {

        return this.cellControllers[rowIndex][colIndex].getFlaggedBombsCounter() >=
                this.gameMainModel.getCell(rowIndex, colIndex).getBombsAroundCellCount();
    }
}