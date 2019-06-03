package model.game;

import model.game.field.cell.Cell;
import model.game.field.IField;
import model.game.field.GameField;
import model.game.field.cell.CellStatus;

/**
 * The main class of the game model.
 */
public class GameModel implements IModel {

    private final IField field;
    private final GameProperties gameProperties;
    private int flagCount = 0;
    private GameState gameState = GameState.PLAY;

    /**
     * Constructor based on game properties.
     *
     * @param gameProperties game properties containing number of bombs of the game, number of rows and columns game field
     * @throws TableGenerationException when the number of bombs and the number of cells of the playing field are not compatible
     */
    public GameModel(GameProperties gameProperties) throws TableGenerationException {
        this.gameProperties = gameProperties;
        field = new GameField(gameProperties);
        if (gameProperties.getBombsCount() > gameProperties.getRows() * gameProperties.getCols()) {
            throw (new TableGenerationException("Error generating bombs on the field, " +
                    "their number must be less than the number of cells in the field"));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameState getGameState() {

        return this.gameState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cell getCell(int rowIndex, int colIndex) {
        Cell cell = this.field.openCell(rowIndex, colIndex);
        if (cell.isBomb()) {
            this.gameState = GameState.LOSE;
        }
        checkWin();

        return cell;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CellStatus changeCellStatus(int rowIndex, int colIndex) {
        switch (this.field.getCellStatus(rowIndex, colIndex)) {
            case CLOSE: {
                this.field.setCellStatus(rowIndex, colIndex, CellStatus.FLAG);
                this.flagCount++;
                checkWin();
                break;
            }
            case FLAG: {
                this.field.setCellStatus(rowIndex, colIndex, CellStatus.CLOSE);
                this.flagCount--;
                break;
            }
        }
        checkWin();
        return this.field.getCellStatus(rowIndex, colIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getFlagCount() {

        return this.flagCount;
    }

    /**
     * Changes the game status if the user correctly set the flags and opened all the cells
     */
    private void checkWin() {
        if (this.flagCount == this.gameProperties.getBombsCount()) {
            for (int i = 0; i < this.gameProperties.getRows(); i++) {
                for (int j = 0; j < this.gameProperties.getCols(); j++) {
                    if (this.field.getCellStatus(i, j).equals(CellStatus.CLOSE)) {

                        return;
                    }
                }
            }
            this.gameState = GameState.WIN;
        }
    }
}