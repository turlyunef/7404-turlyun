package model.game.field;

import model.game.GameProperties;
import model.game.field.cell.Cell;
import model.game.field.cell.CellContent;
import model.game.field.cell.CellStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Playing field class.
 */
public class GameField implements Field {
    private static final Logger log = LoggerFactory.getLogger(GameField.class);
    private final int rowsCount;
    private final int colCount;
    private final Cell[][] cells;
    private final List<Cell> bombs = new ArrayList<>();

    /**
     * Creates a playing field based on game properties.
     *
     * @param gameProperties game properties containing number of bombs of the game, number of rows and columns game field
     */
    public GameField(GameProperties gameProperties) {
        this.rowsCount = gameProperties.getRows();
        this.colCount = gameProperties.getCols();
        this.cells = new Cell[rowsCount][colCount];
        generateBombs(gameProperties.getBombsCount());
        createCells();
        countBombsAroundCells();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cell getCell(int rowIndex, int colIndex) {

        return this.cells[rowIndex][colIndex];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CellStatus getCellStatus(int rowIndex, int colIndex) {

        return this.cells[rowIndex][colIndex].getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCellStatus(int rowIndex, int colIndex, CellStatus cellStatus) {
        this.cells[rowIndex][colIndex].setStatus(cellStatus);
        if (cellStatus.equals(CellStatus.CLOSE)) {
            changeFlaggedBombsCountersInCellsAround(rowIndex, colIndex, -1);
        } else if (cellStatus.equals(CellStatus.FLAG)) {
            changeFlaggedBombsCountersInCellsAround(rowIndex, colIndex, 1);
        }
    }

    /**
     * Changes the counter of the bombs counter in the cell.
     *
     * @param rowIndex index on the rows of this cell
     * @param colIndex index on the columns of this cell
     * @param value    value to which the counter changes
     */
    private void changeFlaggedBombsCountersInCellsAround(int rowIndex, int colIndex, int value) {
        for (int i = rowIndex - 1; i <= rowIndex + 1; i++) {
            for (int j = colIndex - 1; j <= colIndex + 1; j++) {
                if (isCellExist(i, j)) {
                    this.cells[i][j].changeFlaggedBombsCounter(value);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Cell> getBombs() {

        return this.bombs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Cell> getAllFlaggedCells() {
        List<Cell> flaggedCells = new ArrayList<>();
        for (Cell[] rows : cells) {
            for (Cell cell : rows) {
                if (cell.getStatus().equals(CellStatus.FLAG)) {
                    flaggedCells.add(cell);
                }
            }
        }

        return flaggedCells;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean cellsAroundIsFlagged(int rowIndex, int colIndex) {

        return this.cells[rowIndex][colIndex].getFlaggedBombsCounter() >=
                this.cells[rowIndex][colIndex].getBombsAroundCellCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCellExist(int rowIndex, int colIndex) {

        return !((rowIndex < 0) || (colIndex < 0) ||
                (rowIndex >= this.cells.length) ||
                (colIndex >= this.cells[0].length));
    }

    /**
     * Creates a field with a bomb in random order.
     *
     * @param bombsCount number of bombs
     */
    private void generateBombs(int bombsCount) {
        int bombsCounter = 0;
        while (bombsCounter < bombsCount) {
            Random random = new Random();
            int randomRowIndex = random.nextInt(this.rowsCount);
            int randomColIndex = random.nextInt(this.colCount);
            if (this.cells[randomRowIndex][randomColIndex] == null) {
                this.cells[randomRowIndex][randomColIndex] = new Cell(CellContent.BOMB, randomRowIndex, randomColIndex);
                this.bombs.add(this.cells[randomRowIndex][randomColIndex]);
            } else {
                continue;
            }
            bombsCounter++;
        }
        log.debug(bombsCounter + " bombs are created in the game field");
    }

    /**
     * Creates a field with empty content if it does not created with a bomb before.
     */
    private void createCells() {
        for (int i = 0; i < this.rowsCount; i++) {
            for (int j = 0; j < colCount; j++) {
                if (cells[i][j] == null) {
                    cells[i][j] = new Cell(CellContent.EMPTY, i, j);
                }
            }
        }
    }

    /**
     * Counts the number of bombs around the cells.
     */
    private void countBombsAroundCells() {
        for (int i = 0; i < this.rowsCount; i++) {
            for (int j = 0; j < colCount; j++) {
                if (this.cells[i][j].getContent().equals(CellContent.EMPTY)) {
                    this.cells[i][j].setBombsAroundCellCount(countBombsAroundCell(i, j));
                }
            }
        }
    }

    /**
     * Counts the number of bombs around a given cell.
     *
     * @param rowIndex index on the rows of this cell
     * @param colIndex index on the columns of this cell
     * @return number of bombs around the field
     */
    private int countBombsAroundCell(int rowIndex, int colIndex) {
        int bombsAroundCellCounter = 0;
        if (rowIndex > 0) {
            bombsAroundCellCounter += countRowBombs(rowIndex - 1, colIndex);
        }
        if (rowIndex < rowsCount - 1) {
            bombsAroundCellCounter += countRowBombs(rowIndex + 1, colIndex);
        }
        bombsAroundCellCounter += countRowBombs(rowIndex, colIndex);

        return bombsAroundCellCounter;
    }

    /**
     * Counts the number of bombs around the cell in its row.
     *
     * @param rowIndex           index of the counting row
     * @param actualCellColIndex index on the columns of this cell
     * @return number of bombs around the cell in its row
     */
    private int countRowBombs(int rowIndex, int actualCellColIndex) {
        int bombsAroundCellCounter = 0;
        if ((actualCellColIndex > 0) && (cells[rowIndex][actualCellColIndex - 1].isBomb())) {
            bombsAroundCellCounter++;
        }
        if ((actualCellColIndex < colCount - 1) && (cells[rowIndex][actualCellColIndex + 1].isBomb())) {
            bombsAroundCellCounter++;
        }
        if (cells[rowIndex][actualCellColIndex].isBomb()) {
            bombsAroundCellCounter++;
        }

        return bombsAroundCellCounter;
    }
}