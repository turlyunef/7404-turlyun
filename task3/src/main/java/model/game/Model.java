package model.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class Model {

    private static Logger log = LoggerFactory.getLogger(Model.class);

    private int rowsCount;
    private int columnsCount;
    private int bombsCount;
    private final Cell[][] table;
    private GameState state = GameState.PLAY;

    public Model(GameProperties gameProperties) throws TableGenerationException {
        this.rowsCount = gameProperties.getRows();
        this.columnsCount = gameProperties.getCols();
        this.bombsCount = gameProperties.getBombsCount();
        this.table = new Cell[rowsCount][columnsCount];
        if (this.bombsCount > rowsCount * columnsCount) {
            throw (new TableGenerationException("Error generating bombs on the field, " +
                    "their number must be less than the number of cells in the field"));
        }
        generateBombs(this.bombsCount);
        calculateBombsAroundCells();
    }

    public Cell getCell(int rowIndex, int columnIndex) {
        if (cellIsBomb(rowIndex, columnIndex)) {
            this.state = GameState.LOSE;
        }

        return this.table[rowIndex][columnIndex];
    }

    public GameState getState() {
        return state;
    }

    public int getBombsCount() {
        return bombsCount;
    }

    private void generateBombs(int bombsCount) {
        int bombsCounter = 0;
        while (bombsCounter < bombsCount) {
            Random random = new Random();
            int randomRowIndex = random.nextInt(this.rowsCount);
            int randomColumnIndex = random.nextInt(this.columnsCount);
            if (this.table[randomRowIndex][randomColumnIndex] == null) {
                this.table[randomRowIndex][randomColumnIndex] = new Cell(CellContent.BOMB);
            } else {
                continue;
            }
            bombsCounter++;
        }
        log.debug(bombsCounter + " bombs are created in the table");
    }

    private void calculateBombsAroundCells() {
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                if (this.table[i][j] == null) {
                    this.table[i][j] = new Cell(CellContent.EMPTY);
                    this.table[i][j].setBombsAroundCellCount(calculateBombsAroundCell(i, j));
                }
            }
        }
    }

    private int calculateBombsAroundCell(int rowIndex, int columnIndex) {
        int bombsAroundCellCounter = 0;
        if (rowIndex > 0) {
            bombsAroundCellCounter += calculateRowBombs(rowIndex - 1, columnIndex);
        }
        if (rowIndex < rowsCount - 1) {
            bombsAroundCellCounter += calculateRowBombs(rowIndex + 1, columnIndex);
        }
        bombsAroundCellCounter += calculateRowBombs(rowIndex, columnIndex);

        return bombsAroundCellCounter;
    }

    private int calculateRowBombs(int rowIndex, int actualCellColumnIndex) {
        int bombsAroundCellCounter = 0;
        if ((actualCellColumnIndex > 0) && (cellIsBomb(rowIndex, actualCellColumnIndex - 1))) {
            bombsAroundCellCounter++;
        }
        if ((actualCellColumnIndex < columnsCount - 1) && (cellIsBomb(rowIndex, actualCellColumnIndex + 1))) {
            bombsAroundCellCounter++;
        }
        if (cellIsBomb(rowIndex, actualCellColumnIndex)) {
            bombsAroundCellCounter++;
        }

        return bombsAroundCellCounter;
    }

    private boolean cellIsBomb(int rowIndex, int columnIndex) {

        return (this.table[rowIndex][columnIndex] != null) && (this.table[rowIndex][columnIndex].getCellContent() == CellContent.BOMB);
    }
}