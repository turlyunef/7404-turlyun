package model.game.field.outside;

import model.game.GameProperties;
import model.game.GameState;
import model.game.field.Model;

public class OutsideValueGameModel implements Model, OutsideModel {
    private final GameProperties gameProperties;
    private final CellStatus[][] cellStatuses;
    private int flagCount = 0;
    private GameState localState = GameState.PLAY;

    public OutsideValueGameModel(GameProperties gameProperties) {
        this.gameProperties = gameProperties;
        cellStatuses = new CellStatus[this.gameProperties.getRows()][this.gameProperties.getCols()];
        closeCells();
    }

    @Override
    public CellStatus changeCellStatus(int rowIndex, int columnIndex) {
        switch (this.cellStatuses[rowIndex][columnIndex]) {
            case CLOSE: {
                this.cellStatuses[rowIndex][columnIndex] = CellStatus.FLAG;
                this.flagCount++;
                checkWin();
                break;
            }
            case FLAG: {
                this.cellStatuses[rowIndex][columnIndex] = CellStatus.CLOSE;
                this.flagCount--;
                break;
            }
        }
        return this.cellStatuses[rowIndex][columnIndex];
    }

    @Override
    public GameState getGameState(){

        return localState;
    }

    @Override
    public void setOpen(int rowIndex, int columnIndex) {
        this.cellStatuses[rowIndex][columnIndex] = CellStatus.OPEN;
        checkWin();
    }

    @Override
    public int getFlagCount() {
        return flagCount;
    }

    private void closeCells() {
        for (int i = 0; i < this.gameProperties.getRows(); i++) {
            for (int j = 0; j < this.gameProperties.getCols(); j++) {
                cellStatuses[i][j] = CellStatus.CLOSE;
            }
        }
    }

    private void checkWin() {
        if (this.flagCount == this.gameProperties.getBombsCount()) {
            for (int i = 0; i < this.gameProperties.getRows(); i++) {
                for (int j = 0; j < this.gameProperties.getCols(); j++) {
                    if (this.cellStatuses[i][j].equals(CellStatus.CLOSE)) {

                        return;
                    }
                }
            }
            localState = GameState.WIN;
        }
    }
}