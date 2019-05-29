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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public GameState getGameState(){

        return this.localState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOpen(int rowIndex, int colIndex) {
        this.cellStatuses[rowIndex][colIndex] = CellStatus.OPEN;
        checkWin();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getFlagCount() {
        return this.flagCount;
    }

    /**
     * Sets all cells of the playing field closed cell status
     */
    private void closeCells() {
        for (int i = 0; i < this.gameProperties.getRows(); i++) {
            for (int j = 0; j < this.gameProperties.getCols(); j++) {
                this.cellStatuses[i][j] = CellStatus.CLOSE;
            }
        }
    }

    /**
     * Changes the game status if the user correctly set the flags and opened all the cells
     */
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