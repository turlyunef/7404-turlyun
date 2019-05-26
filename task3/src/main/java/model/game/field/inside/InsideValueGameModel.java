package model.game.field.inside;

import model.game.GameProperties;
import model.game.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class InsideValueGameModel implements InsideModel {

    private static final Logger log = LoggerFactory.getLogger(InsideValueGameModel.class);

    private final int rowsCount;
    private final int columnsCount;
    private final Cell[][] cellsValues;
    private GameState gameState = GameState.PLAY;

    public InsideValueGameModel(GameProperties gameProperties) {
        this.rowsCount = gameProperties.getRows();
        this.columnsCount = gameProperties.getCols();
        this.cellsValues = new Cell[rowsCount][columnsCount];
        generateBombs(gameProperties.getBombsCount());
        calculateBombsAroundCells();
    }

    @Override
    public GameState getGameState() {

        return this.gameState;
    }

    @Override
    public Cell getCell(int rowIndex, int columnIndex) {
        if (this.cellsValues[rowIndex][columnIndex].isCellBomb()) {
            this.gameState = GameState.LOSE;
        }

        return this.cellsValues[rowIndex][columnIndex];
    }

    private void generateBombs(int bombsCount) {
        int bombsCounter = 0;
        while (bombsCounter < bombsCount) {
            Random random = new Random();
            int randomRowIndex = random.nextInt(this.rowsCount);
            int randomColumnIndex = random.nextInt(this.columnsCount);
            if (this.cellsValues[randomRowIndex][randomColumnIndex] == null) {
                this.cellsValues[randomRowIndex][randomColumnIndex] = new Cell(CellContent.BOMB);
            } else {
                continue;
            }
            bombsCounter++;
        }
        log.debug(bombsCounter + " bombs are created in the cellsValues");
    }

    private void calculateBombsAroundCells() {
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                if (this.cellsValues[i][j] == null) {
                    this.cellsValues[i][j] = new Cell(CellContent.EMPTY);
                    this.cellsValues[i][j].setBombsAroundCellCount(calculateBombsAroundCell(i, j));
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

        return (this.cellsValues[rowIndex][columnIndex] != null) &&
                (this.cellsValues[rowIndex][columnIndex].getCellContent() == CellContent.BOMB);
    }
}