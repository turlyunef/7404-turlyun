package controller;

import model.game.GameState;
import model.game.Model;

public class Controllers {
    private AbstractController[][] controllers;
    private Model model;

    public Controllers(AbstractController[][] controllers, Model model) {
        this.controllers = controllers;
        this.model = model;
    }

    public void setController(ButtonController buttonController, int rowIndex, int columnIndex) {
        this.controllers[rowIndex][columnIndex] = buttonController;
    }

    private boolean isCellExist(int rowIndex, int columnIndex) {

        return !((rowIndex < 0) | (columnIndex < 0) | (rowIndex >= controllers.length) | (columnIndex >= controllers[0].length));
    }

    private void openCell(int rowIndex, int columnIndex) {
        if ((gameNotLoss()) && (cellIsClose(rowIndex, columnIndex))) {
            if (model.cellIsBomb(rowIndex, columnIndex)) {
                explode(rowIndex, columnIndex);
            } else {
                int bombsAroundCellCount = model.getCell(rowIndex, columnIndex).getBombsAroundCellCount();
                controllers[rowIndex][columnIndex].setOpenCell(bombsAroundCellCount);
                if (bombsAroundCellCount == 0) {
                    openCellsAround(rowIndex, columnIndex);
                }
            }
        }
    }

    private void explode(int rowIndex, int columnIndex) {
        showAllBombs();
        controllers[rowIndex][columnIndex].setExplodedMineCell();
        this.model.setState(GameState.LOSE);
    }

    private void openCellsAround(int rowIndex, int columnIndex) {
        for (int i = rowIndex - 1; i <= rowIndex + 1; i++) {
            for (int j = columnIndex - 1; j <= columnIndex + 1; j++) {
                if (isCellExist(i, j)) {
                    openCell(i, j);
                }
            }
        }
    }

    private boolean gameNotLoss() {

        return !this.model.getState().equals(GameState.LOSE);
    }

    private boolean cellIsClose(int rowIndex, int columnIndex) {

        return controllers[rowIndex][columnIndex].getCellStatus().equals(CellStatus.CLOSE);
    }

    private void showAllBombs() {
        for (int i = 0; i < this.controllers.length; i++) {
            for (int j = 0; j < this.controllers[i].length; j++) {
                if (this.model.cellIsBomb(i, j)) {
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

    private void changeCellStatus(int rowIndex, int columnIndex) {
        switch (this.controllers[rowIndex][columnIndex].getCellStatus()) {
            case CLOSE: {
                this.controllers[rowIndex][columnIndex].setFlag();
                changeDefusedBombsCountersInCellsAround(rowIndex, columnIndex, 1);
                break;
            }
            case FLAG: {
                this.controllers[rowIndex][columnIndex].setClose();
                changeDefusedBombsCountersInCellsAround(rowIndex, columnIndex, -1);
                break;
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

    public void newGame(Model model, AbstractController[][] abstractControllers) {
        this.model = model;
        this.controllers = abstractControllers;
        closeAllCells();
    }

    private void closeAllCells() {
        for (int i = 0; i < this.controllers.length; i++) {
            for (int j = 0; j < this.controllers[i].length; j++) {
                this.controllers[i][j].setClose();
            }
        }
    }

    public void releasedButton1(int rowIndex, int columnIndex) {
        if (gameNotLoss()) {
            openCell(rowIndex, columnIndex);
        }
    }

    public void releasedButton2(int rowIndex, int colIndex) {
        if ((gameNotLoss()) && (!cellIsClose(rowIndex, colIndex)) && (cellsAroundDemine(rowIndex, colIndex))) {
            openCellsAround(rowIndex, colIndex);
        }
    }

    private boolean cellsAroundDemine(int rowIndex, int colIndex) {

        return this.controllers[rowIndex][colIndex].getFlaggedBombsCounter() >=
                model.getCell(rowIndex, colIndex).getBombsAroundCellCount();
    }

    public void pressedButton3(int rowIndex, int columnIndex) {
        if (gameNotLoss()) {
            changeCellStatus(rowIndex, columnIndex);
        }
    }
}