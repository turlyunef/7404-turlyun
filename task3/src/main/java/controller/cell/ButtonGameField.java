package controller.cell;

import model.game.GameState;
import model.game.Model;

public class ButtonGameField implements GameField {

    private final Model gameModel;
    private int flagCount;
    private GameState gameState = GameState.PLAY;
    private AbstractController[][] controllers;

    public ButtonGameField(Model gameModel, AbstractController[][] controllers) {
        this.gameModel = gameModel;
        this.flagCount = 0;
        this.controllers = controllers;
    }

    @Override
    public void tryOpenCell(int rowIndex, int columnIndex) {
        if (checkGameNotEnded() && (cellIsClose(rowIndex, columnIndex))) {
            if (gameModel.getCell(rowIndex, columnIndex).isCellBomb()) {
                explode(rowIndex, columnIndex);
            } else {
                int bombsAroundCellCount = gameModel.getCell(rowIndex, columnIndex).getBombsAroundCellCount();
                controllers[rowIndex][columnIndex].setOpenCell(bombsAroundCellCount);
                checkWin();
                if (bombsAroundCellCount == 0) {
                    openCellsAround(rowIndex, columnIndex);
                }
            }
        }
    }

    @Override
    public void changeCellStatus(int rowIndex, int columnIndex) {
        switch (this.controllers[rowIndex][columnIndex].getCellStatus()) {
            case CLOSE: {
                this.controllers[rowIndex][columnIndex].setFlag();
                changeDefusedBombsCountersInCellsAround(rowIndex, columnIndex, 1);
                this.flagCount++;
                checkWin();
                break;
            }
            case FLAG: {
                this.controllers[rowIndex][columnIndex].setClose();
                changeDefusedBombsCountersInCellsAround(rowIndex, columnIndex, -1);
                this.flagCount--;
                break;
            }
        }
    }

    @Override
    public GameState getGameState() {

            return this.gameModel.getState();
    }


    @Override
    public void openCellsAround(int rowIndex, int colIndex) {
        if ((!cellIsClose(rowIndex, colIndex)) && (cellsAroundDemine(rowIndex, colIndex))) {
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
    public void setController(ButtonController buttonController, int rowIndex, int columnIndex) {
        this.controllers[rowIndex][columnIndex] = buttonController;
    }

    private boolean checkGameNotEnded() {

        return (!this.gameModel.getState().equals(GameState.LOSE)) && (!this.gameState.equals(GameState.WIN));
    }

    private void checkWin() {
        if (this.flagCount == this.gameModel.getBombsCount()) {
            for (AbstractController[] controller : controllers) {
                for (AbstractController abstractController : controller) {
                    if (abstractController.cellStatus.equals(CellStatus.CLOSE)) {

                        return;
                    }
                }
            }
            setWin();
        }
    }

    private void setWin() {
        this.gameState = GameState.WIN;
    }

    private void explode(int rowIndex, int columnIndex) {
        showAllBombs();
        controllers[rowIndex][columnIndex].setExplodedMineCell();
    }


    private boolean cellIsClose(int rowIndex, int columnIndex) {

        return controllers[rowIndex][columnIndex].getCellStatus().equals(CellStatus.CLOSE);
    }

    private void showAllBombs() {
        for (int i = 0; i < this.controllers.length; i++) {
            for (int j = 0; j < this.controllers[i].length; j++) {
                if (this.gameModel.getCell(i, j).isCellBomb()) {
                    if (this.controllers[i][j].getCellStatus().equals(CellStatus.CLOSE)) {
                        this.controllers[i][j].setMineCell();
                    }
                } else {
                    if (this.controllers[i][j].getCellStatus().equals(CellStatus.FLAG)) {
                        this.controllers[i][j].setFalseMinedCell();
                    }
                }
            }
        }
    }

    private void changeDefusedBombsCountersInCellsAround(int rowIndex, int columnIndex, int value) {
        for (int i = rowIndex - 1; i <= rowIndex + 1; i++) {
            for (int j = columnIndex - 1; j <= columnIndex + 1; j++) {
                if (isCellExist(i, j)) {
                    controllers[i][j].changeFlaggedBombsCounter(value);
                }
            }
        }
    }

    private boolean isCellExist(int rowIndex, int columnIndex) {

        return !((rowIndex < 0) | (columnIndex < 0) | (rowIndex >= controllers.length) | (columnIndex >= controllers[0].length));
    }

    private boolean cellsAroundDemine(int rowIndex, int colIndex) {

        return this.controllers[rowIndex][colIndex].getFlaggedBombsCounter() >=
                gameModel.getCell(rowIndex, colIndex).getBombsAroundCellCount();
    }
}