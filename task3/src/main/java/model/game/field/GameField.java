package model.game.field;

import model.game.GameProperties;
import model.game.field.cell.Cell;
import model.game.field.cell.CellContent;
import model.game.field.cell.CellStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Playing field class.
 */
public class GameField implements IField {
    private static final Logger log = LoggerFactory.getLogger(GameField.class);
    private final int rowsCount;
    private final int colCount;
    private final Cell[][] cells;

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
    public Cell openCell(int rowIndex, int colIndex) {

        this.cells[rowIndex][colIndex].setStatus(CellStatus.OPEN);

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
    }

    /**
     * Creates a cell with a bomb in random order.
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
                this.cells[randomRowIndex][randomColIndex] = new Cell(CellContent.BOMB);
            } else {
                continue;
            }
            bombsCounter++;
        }
        log.debug(bombsCounter + " bombs are created in the game field");
    }

    /**
     * Creates a cell with empty content if it is not created with a bomb before.
     */
    private void createCells() {
        for (int i = 0; i < this.rowsCount; i++) {
            for (int j = 0; j < colCount; j++) {
                if (cells[i][j] == null) {
                    cells[i][j] = new Cell(CellContent.EMPTY);
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
     * @return number of bombs around the cell
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
     * @param rowIndex           index on the rows for counting
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