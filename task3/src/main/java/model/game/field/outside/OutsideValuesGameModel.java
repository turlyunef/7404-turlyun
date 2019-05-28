package model.game.field.outside;

import model.game.GameProperties;
import model.game.GameState;
import model.game.field.Model;

public class OutsideValuesGameModel implements Model, OutsideModel {
    private final GameProperties gameProperties;
    private final CellStatus[][] cellStatuses;
    private int flagCount = 0;
    private GameState localState = GameState.PLAY;

    public OutsideValuesGameModel(GameProperties gameProperties) {
        this.gameProperties = gameProperties;
        this.cellStatuses = new CellStatus[this.gameProperties.getRows()][this.gameProperties.getCols()];
        closeCells();
    }

    @Override
    public CellStatus changeCellStatus(int rowIndex, int colIndex) {
        switch (this.cellStatuses[rowIndex][colIndex]) {
            case CLOSE: {
                this.cellStatuses[rowIndex][colIndex] = CellStatus.FLAG;
                this.flagCount++;
                checkWin();
                break;
            }
            case FLAG: {
                this.cellStatuses[rowIndex][colIndex] = CellStatus.CLOSE;
                this.flagCount--;
                break;
            }
        }
        return this.cellStatuses[rowIndex][colIndex];
    }

    @Override
    public GameState getGameState(){

        return this.localState;
    }

    @Override
    public void setOpen(int rowIndex, int colIndex) {
        this.cellStatuses[rowIndex][colIndex] = CellStatus.OPEN;
        checkWin();
    }

    @Override
    public int getFlagCount() {
        return this.flagCount;
    }

    private void closeCells() {
        for (int i = 0; i < this.gameProperties.getRows(); i++) {
            for (int j = 0; j < this.gameProperties.getCols(); j++) {
                this.cellStatuses[i][j] = CellStatus.CLOSE;
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
            this.localState = GameState.WIN;
        }
    }
}