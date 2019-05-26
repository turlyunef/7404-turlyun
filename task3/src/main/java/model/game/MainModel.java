package model.game;

import model.game.field.Model;
import model.game.field.inside.Cell;
import model.game.field.inside.CellContent;
import model.game.field.inside.InsideModel;
import model.game.field.inside.InsideValueGameModel;
import model.game.field.outside.CellStatus;
import model.game.field.outside.OutsideModel;
import model.game.field.outside.OutsideValueGameModel;

public class MainModel implements Model {

    private final InsideModel insideValues;
    private final OutsideModel outsideValues;
    private GameState gameState = GameState.PLAY;

    public MainModel(GameProperties gameProperties) throws TableGenerationException {
        insideValues = new InsideValueGameModel(gameProperties);
        outsideValues = new OutsideValueGameModel(gameProperties);
        if (gameProperties.getBombsCount() > gameProperties.getRows() * gameProperties.getCols()) {
            throw (new TableGenerationException("Error generating bombs on the field, " +
                    "their number must be less than the number of cells in the field"));
        }
    }

    @Override
    public GameState getGameState() {
        return this.gameState;
    }

    public Cell getCell(int rowIndex, int columnIndex) {
        if (cellIsBomb(rowIndex, columnIndex)) {
            this.gameState = GameState.LOSE;
        }
        this.outsideValues.setOpen(rowIndex, columnIndex);
        checkWin();
        return this.insideValues.getCell(rowIndex, columnIndex);
    }

    public CellStatus changeCellStatus(int rowIndex, int columnIndex) {
        CellStatus cellStatus = outsideValues.changeCellStatus(rowIndex, columnIndex);
        checkWin();

        return cellStatus;
    }

    private void checkWin() {
        if (outsideValues.getGameState().equals(GameState.WIN) && (insideValues.getGameState().equals(GameState.PLAY))) {
            this.gameState = GameState.WIN;
        }
        if (insideValues.getGameState().equals(GameState.LOSE)) {
            this.gameState = GameState.LOSE;
        }
    }

    private boolean cellIsBomb(int rowIndex, int columnIndex) {

        return (this.insideValues.getCell(rowIndex, columnIndex).getCellContent() == CellContent.BOMB);
    }
}