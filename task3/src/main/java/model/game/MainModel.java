package model.game;

import model.game.field.Model;
import model.game.field.inside.Cell;
import model.game.field.inside.CellContent;
import model.game.field.inside.InsideModel;
import model.game.field.inside.InsideValuesGameModel;
import model.game.field.outside.CellStatus;
import model.game.field.outside.OutsideModel;
import model.game.field.outside.OutsideValuesGameModel;

/**
 * The main class of the game model.
 * The model is divided into a cell content model (InsideModel) and a user cell work model (external display model (OutsideModel)).
 * The model can be used on the server to implement the game in the web architecture.
 * The user will not be able to influence the result, since the win check is performed in the model.
 */
public class MainModel implements Model {

    private final InsideModel insideValues;
    private final OutsideModel outsideValues;
    private GameState gameState = GameState.PLAY;

    /**
     * Constructor based on game properties.
     *
     * @param gameProperties game properties containing number of bombs of the game, number of rows and columns game field
     * @throws TableGenerationException when the number of bombs and the number of cells of the playing field are not compatible
     */
    public MainModel(GameProperties gameProperties) throws TableGenerationException {
        insideValues = new InsideValuesGameModel(gameProperties);
        outsideValues = new OutsideValuesGameModel(gameProperties);
        if (gameProperties.getBombsCount() > gameProperties.getRows() * gameProperties.getCols()) {
            throw (new TableGenerationException("Error generating bombs on the field, " +
                    "their number must be less than the number of cells in the field"));
        }
    }

    /**
     * Returns the state of the game: play, lose or win.
     *
     * @return state of the game
     */
    @Override
    public GameState getGameState() {

        return this.gameState;
    }

    /**
     * Returns a cell of the playing field
     *
     * @param rowIndex index on the rows of this cell
     * @param colIndex index on the columns of this cell
     * @return cell of the playing field
     */
    public Cell getCell(int rowIndex, int colIndex) {
        if (cellIsBomb(rowIndex, colIndex)) {
            this.gameState = GameState.LOSE;
        }
        this.outsideValues.setOpen(rowIndex, colIndex);
        checkWin();

        return this.insideValues.getCell(rowIndex, colIndex);
    }

    /**
     * Changes cell status of the mine clearance
     *
     * @param rowIndex index on the rows of this cell
     * @param colIndex index on the columns of this cell
     * @return cell status of the mine clearance
     */
    public CellStatus changeCellStatus(int rowIndex, int colIndex) {
        CellStatus cellStatus = this.outsideValues.changeCellStatus(rowIndex, colIndex);
        checkWin();

        return cellStatus;
    }

    /**
     * Returns the number of flags set by the user on the playing field
     *
     * @return number of flags
     */
    public int getFlagCount() {

        return this.outsideValues.getFlagCount();
    }

    /**
     * Changes the game status if the user won
     */
    private void checkWin() {
        if (this.outsideValues.getGameState().equals(GameState.WIN) && (this.insideValues.getGameState().equals(GameState.PLAY))) {
            this.gameState = GameState.WIN;
        }
        if (insideValues.getGameState().equals(GameState.LOSE)) {
            this.gameState = GameState.LOSE;
        }
    }

    /**
     * Checks if the cell is a bomb
     *
     * @param rowIndex index on the rows of this cell
     * @param colIndex index on the columns of this cell
     * @return true if the cell is a bomb and false if it is not
     */
    private boolean cellIsBomb(int rowIndex, int colIndex) {

        return (this.insideValues.getCell(rowIndex, colIndex).getCellContent() == CellContent.BOMB);
    }
}