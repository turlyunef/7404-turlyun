package model.game;

import model.game.field.GameField;
import model.game.field.IField;
import model.game.field.cell.Cell;
import model.game.field.cell.CellStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * The main class of the game model.
 */
public class GameModel implements IModel {

    private final IField field;
    private final GameProperties gameProperties;
    private int flagCount = 0;
    private GameState gameState = GameState.PLAY;
    private List<Cell> openingCells;

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
    public List<Cell> getOpenedCellsByOpenCell(int rowIndex, int colIndex) {
        this.openingCells = new ArrayList<>();
        openCell(rowIndex, colIndex);

        return this.openingCells;
    }

    /**
     * TODO
     *
     * @param rowIndex
     * @param colIndex
     */
    private void openCell(int rowIndex, int colIndex) {
        Cell cell = this.field.openCell(rowIndex, colIndex);
        if (cell.getStatus() == CellStatus.CLOSE) {
            this.openingCells.add(cell);
            if (cell.isBomb()) {
                this.gameState = GameState.LOSE;
                cell.setStatus(CellStatus.EXPLODED);
//                this.openingCells.add(cell);
            } else {
                cell.setStatus(CellStatus.OPEN);
//                this.openingCells.add(cell);
                checkWin();
                if (cell.getBombsAroundCellCount() == 0) {
                    openCellsAround(rowIndex, colIndex);

                }
            }
        }
    }

    /**
     * TODO
     *
     * @param rowIndex
     * @param colIndex
     */
    private void openCellsAround(int rowIndex, int colIndex) {
        if (this.field.cellsAroundIsDemined(rowIndex, colIndex)) {
            for (int i = rowIndex - 1; i <= rowIndex + 1; i++) {
                for (int j = colIndex - 1; j <= colIndex + 1; j++) {
                    if (this.field.isCellExist(i, j)) {
                        openCell(i, j);
                    }
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Cell> getOpenedCellsByOpenCellsAround(int rowIndex, int colIndex) {
        this.openingCells = new ArrayList<>();
        openCellsAround(rowIndex, colIndex);

        return this.openingCells;
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
     * {@inheritDoc}
     */
    @Override
    public List<Cell> getAllBombs() {

        if (gameState.equals(GameState.LOSE)) {

            return this.field.getBombs();
        }

        return null;
    }

    /**
     * Changes the game status if the user correctly set the flags and opened all the cells.
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