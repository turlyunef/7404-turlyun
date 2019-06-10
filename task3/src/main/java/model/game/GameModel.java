package model.game;

import model.game.field.Field;
import model.game.field.GameField;
import model.game.field.cell.Cell;
import model.game.field.cell.CellStatus;
import model.game.statistic.Winner;
import model.game.statistic.WinnersRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * The main class of the game model.
 */
public class GameModel implements Model {

    private final WinnersRepository winnersRepository = new WinnersRepository();
    private Field field;
    private GameProperties gameProperties;
    private int flagCount;
    private GameState gameState;
    private List<Cell> openingCells;
    private long gameTime;

    /**
     * {@inheritDoc}
     */
    @Override
    public void restartGameField(GameProperties gameProperties) throws TableGenerationException {
        this.gameTime = 0;
        this.gameProperties = gameProperties;
        this.field = new GameField(gameProperties);
        if (gameProperties.getBombsCount() > gameProperties.getRows() * gameProperties.getCols()) {
            throw (new TableGenerationException("Error generating bombs on the field, " +
                    "their number must be less than the number of cells in the field"));
        }
        this.gameState = GameState.PLAY;
        this.flagCount = 0;
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
        if (this.gameTime == 0) {
            this.gameTime = System.currentTimeMillis();
        }
        this.openingCells = new ArrayList<>();
        openCell(rowIndex, colIndex);

        return this.openingCells;
    }

    /**
     * opens a closed cell depending on its contents and adds it to the list of opened cells.
     * If cells around are empty , opens cells around.
     *
     * @param rowIndex index on the rows of this cell
     * @param colIndex index on the columns of this cell
     */
    private void openCell(int rowIndex, int colIndex) {
        Cell cell = this.field.getCell(rowIndex, colIndex);
        if (cellIsClose(rowIndex, colIndex)) {
            if (cell.isBomb()) {
                this.gameState = GameState.LOSE;
                cell.setStatus(CellStatus.EXPLODED);
            } else {
                cell.setStatus(CellStatus.OPEN);
                checkWin();
                if (cell.getBombsAroundCellCount() == 0) {
                    openCellsAround(rowIndex, colIndex);
                }
            }
            this.openingCells.add(cell);
        }
    }

    /**
     * Opens cells around a given one if the number of flags around coincides with the number of bombs.
     *
     * @param rowIndex index on the rows of this cell
     * @param colIndex index on the columns of this cell
     */
    private void openCellsAround(int rowIndex, int colIndex) {
        if (this.field.cellsAroundIsFlagged(rowIndex, colIndex)) {
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
        if ((gameState.equals(GameState.PLAY) && (cellIsOpen(rowIndex, colIndex)))) {
            openCellsAround(rowIndex, colIndex);
        }

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
     * {@inheritDoc}
     */
    @Override
    public List<Cell> getFlaggedCells() {

        return this.field.getAllFlaggedCells();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Winner> getWinners() {

        return this.winnersRepository.getWinners();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getGameTime() {
        if (this.gameTime == 0) {

            return 0;
        }
        return (float) (System.currentTimeMillis() - this.gameTime) / 1000;
    }

    /**
     * Changes the game status to win if a user correctly set the flags and opened all the cells.
     */
    private void checkWin() {
        if (this.flagCount == this.gameProperties.getBombsCount()) {
            for (int i = 0; i < this.gameProperties.getRows(); i++) {
                for (int j = 0; j < this.gameProperties.getCols(); j++) {
                    if (cellIsClose(i, j)) {

                        return;
                    }
                }
            }
            setWin();
        }
    }

    /**
     * Checks if cell status is closed.
     *
     * @param rowIndex index on the rows of this cell
     * @param colIndex index on the columns of this cell
     * @return true if cell status is closed else false
     */
    private boolean cellIsClose(int rowIndex, int colIndex) {

        return this.field.getCellStatus(rowIndex, colIndex).equals(CellStatus.CLOSE);
    }

    /**
     * Checks if cell status is opened.
     *
     * @param rowIndex index on the rows of this cell
     * @param colIndex index on the columns of this cell
     * @return true if cell status is opened else false
     */
    private boolean cellIsOpen(int rowIndex, int colIndex) {

        return this.field.getCellStatus(rowIndex, colIndex).equals(CellStatus.OPEN);
    }

    /**
     * Initializes the current game to victory.
     */
    private void setWin() {
        this.gameState = GameState.WIN;
        this.winnersRepository.addWinner(gameProperties, getGameTime());
    }
}