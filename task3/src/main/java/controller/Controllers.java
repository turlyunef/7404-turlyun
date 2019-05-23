package controller;

import controller.statistic.Winner;
import model.game.GameState;
import model.game.Model;

import java.util.ArrayList;

public class Controllers {
    private AbstractController[][] controllers;
    private Model model;
    private RestartButtonController restartButtonController;
    private int flagCount;
    private long startTime;
    private ArrayList<Winner> winners = new ArrayList<>();

    public Controllers(AbstractController[][] controllers, Model model, RestartButtonController restartButtonController) {
        startTime = System.currentTimeMillis();
        this.controllers = controllers;
        this.model = model;
        this.restartButtonController = restartButtonController;
        this.flagCount = 0;
        restartButtonController.setPlayButton();
    }

    public void setCellButtonController(ButtonController buttonController, int rowIndex, int columnIndex) {
        this.controllers[rowIndex][columnIndex] = buttonController;
    }

    public void releasedButton1(int rowIndex, int columnIndex) {
        if (checkGameNotEnded()) {
            openCell(rowIndex, columnIndex);
        }
    }

    public void releasedButton2(int rowIndex, int colIndex) {
        if ((checkGameNotEnded()) && (!cellIsClose(rowIndex, colIndex)) && (cellsAroundDemine(rowIndex, colIndex))) {
            openCellsAround(rowIndex, colIndex);
        }
    }

    public void pressedButton3(int rowIndex, int columnIndex) {
        if (checkGameNotEnded()) {
            changeCellStatus(rowIndex, columnIndex);
        }
    }

    private void setLost() {
        this.restartButtonController.setLostButton();

        this.restartButtonController.setGameState(GameState.LOSE);
    }

    private void setWin() {
        this.restartButtonController.setWinButton();
        this.restartButtonController.setGameState(GameState.WIN);
        winners.add(new Winner(this.model.getGameProperties(), getPlayTime()));
    }



    private int getPlayTime(){
        return (int) (System.currentTimeMillis() - startTime)/1000;
    }

    private boolean isCellExist(int rowIndex, int columnIndex) {

        return !((rowIndex < 0) | (columnIndex < 0) | (rowIndex >= controllers.length) | (columnIndex >= controllers[0].length));
    }

    private void openCell(int rowIndex, int columnIndex) {
        if ((checkGameNotEnded()) && (cellIsClose(rowIndex, columnIndex))) {
            if (model.getCell(rowIndex, columnIndex).isCellBomb()) {
                explode(rowIndex, columnIndex);
            } else {
                int bombsAroundCellCount = model.getCell(rowIndex, columnIndex).getBombsAroundCellCount();
                controllers[rowIndex][columnIndex].setOpenCell(bombsAroundCellCount);
                checkWin();
                if (bombsAroundCellCount == 0) {
                    openCellsAround(rowIndex, columnIndex);
                }
            }
        }
    }

    private void checkWin() {
        if (this.flagCount == this.model.getBombsCount()) {
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

    private void explode(int rowIndex, int columnIndex) {
        showAllBombs();
        setLost();
        controllers[rowIndex][columnIndex].setExplodedMineCell();
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

    private boolean checkGameNotEnded() {

        return (!this.model.getState().equals(GameState.LOSE)) & (!this.restartButtonController.getGameState().equals(GameState.WIN));
    }

    private boolean cellIsClose(int rowIndex, int columnIndex) {

        return controllers[rowIndex][columnIndex].getCellStatus().equals(CellStatus.CLOSE);
    }

    private void showAllBombs() {
        for (int i = 0; i < this.controllers.length; i++) {
            for (int j = 0; j < this.controllers[i].length; j++) {
                if (this.model.getCell(i, j).isCellBomb()) {
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

    private void changeDefusedBombsCountersInCellsAround(int rowIndex, int columnIndex, int value) {
        for (int i = rowIndex - 1; i <= rowIndex + 1; i++) {
            for (int j = columnIndex - 1; j <= columnIndex + 1; j++) {
                if (isCellExist(i, j)) {
                    controllers[i][j].changeFlaggedBombsCounter(value);
                }
            }
        }
    }

    public ArrayList<Winner> getWinners() {
        return winners;
    }

    private boolean cellsAroundDemine(int rowIndex, int colIndex) {

        return this.controllers[rowIndex][colIndex].getFlaggedBombsCounter() >=
                model.getCell(rowIndex, colIndex).getBombsAroundCellCount();
    }
}