package model.game;

import model.game.field.Model;
import model.game.field.inside.Cell;
import model.game.field.inside.CellContent;
import model.game.field.inside.InsideModel;
import model.game.field.inside.InsideValuesGameModel;
import model.game.field.outside.CellStatus;
import model.game.field.outside.OutsideModel;
import model.game.field.outside.OutsideValuesGameModel;

public class MainModel implements Model {

    private final InsideModel insideValues;
    private final OutsideModel outsideValues;
    private GameState gameState = GameState.PLAY;

    public MainModel(GameProperties gameProperties) throws TableGenerationException {
        insideValues = new InsideValuesGameModel(gameProperties);
        outsideValues = new OutsideValuesGameModel(gameProperties);
        if (gameProperties.getBombsCount() > gameProperties.getRows() * gameProperties.getCols()) {
            throw (new TableGenerationException("Error generating bombs on the field, " +
                    "their number must be less than the number of cells in the field"));
        }
    }

    @Override
    public GameState getGameState() {

        return this.gameState;
    }

    public Cell getCell(int rowIndex, int colIndex) {
        if (cellIsBomb(rowIndex, colIndex)) {
            this.gameState = GameState.LOSE;
        }
        this.outsideValues.setOpen(rowIndex, colIndex);
        checkWin();

        return this.insideValues.getCell(rowIndex, colIndex);
    }

    public CellStatus changeCellStatus(int rowIndex, int colIndex) {
        CellStatus cellStatus = this.outsideValues.changeCellStatus(rowIndex, colIndex);
        checkWin();

        return cellStatus;
    }

    public int getFlagCount() {

        return this.outsideValues.getFlagCount();
    }

    private void checkWin() {
        if (this.outsideValues.getGameState().equals(GameState.WIN) && (this.insideValues.getGameState().equals(GameState.PLAY))) {
            this.gameState = GameState.WIN;
        }
        if (insideValues.getGameState().equals(GameState.LOSE)) {
            this.gameState = GameState.LOSE;
        }
    }

    private boolean cellIsBomb(int rowIndex, int colIndex) {

        return (this.insideValues.getCell(rowIndex, colIndex).getCellContent() == CellContent.BOMB);
    }
}