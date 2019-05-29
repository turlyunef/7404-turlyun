package model.game.field.inside;

import model.game.GameProperties;
import model.game.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class InsideValuesGameModel implements InsideModel {

    private static final Logger log = LoggerFactory.getLogger(InsideValuesGameModel.class);

    private final int rowsCount;
    private final int colCount;
    private final Cell[][] cellsValues;
    private GameState gameState = GameState.PLAY;

    public InsideValuesGameModel(GameProperties gameProperties) {
        this.rowsCount = gameProperties.getRows();
        this.colCount = gameProperties.getCols();
        this.cellsValues = new Cell[rowsCount][colCount];
        generateBombs(gameProperties.getBombsCount());
        calculateBombsAroundCells();
    }

    @Override
    public GameState getGameState() {

        return this.gameState;
    }

    @Override
    public Cell getCell(int rowIndex, int colIndex) {
        if (this.cellsValues[rowIndex][colIndex].isCellBomb()) {
            this.gameState = GameState.LOSE;
        }

        return this.cellsValues[rowIndex][colIndex];
    }

    private void generateBombs(int bombsCount) {
        int bombsCounter = 0;
        while (bombsCounter < bombsCount) {
            Random random = new Random();
            int randomRowIndex = random.nextInt(this.rowsCount);
            int randomColIndex = random.nextInt(this.colCount);
            if (this.cellsValues[randomRowIndex][randomColIndex] == null) {
                this.cellsValues[randomRowIndex][randomColIndex] = new Cell(CellContent.BOMB);
            } else {
                continue;
            }
            bombsCounter++;
        }
        log.debug(bombsCounter + " bombs are created in the game field");
    }

    private void calculateBombsAroundCells() {
        for (int i = 0; i < this.rowsCount; i++) {
            for (int j = 0; j < colCount; j++) {
                if (this.cellsValues[i][j] == null) {
                    this.cellsValues[i][j] = new Cell(CellContent.EMPTY);
                    this.cellsValues[i][j].setBombsAroundCellCount(calculateBombsAroundCell(i, j));
                }
            }
        }
    }

    private int calculateBombsAroundCell(int rowIndex, int colIndex) {
        int bombsAroundCellCounter = 0;
        if (rowIndex > 0) {
            bombsAroundCellCounter += calculateRowBombs(rowIndex - 1, colIndex);
        }
        if (rowIndex < rowsCount - 1) {
            bombsAroundCellCounter += calculateRowBombs(rowIndex + 1, colIndex);
        }
        bombsAroundCellCounter += calculateRowBombs(rowIndex, colIndex);

        return bombsAroundCellCounter;
    }

    private int calculateRowBombs(int rowIndex, int actualCellColumnIndex) {
        int bombsAroundCellCounter = 0;
        if ((actualCellColumnIndex > 0) && (cellIsBomb(rowIndex, actualCellColumnIndex - 1))) {
            bombsAroundCellCounter++;
        }
        if ((actualCellColumnIndex < colCount - 1) && (cellIsBomb(rowIndex, actualCellColumnIndex + 1))) {
            bombsAroundCellCounter++;
        }
        if (cellIsBomb(rowIndex, actualCellColumnIndex)) {
            bombsAroundCellCounter++;
        }

        return bombsAroundCellCounter;
    }

    private boolean cellIsBomb(int rowIndex, int colIndex) {

        return (this.cellsValues[rowIndex][colIndex] != null) &&
                (this.cellsValues[rowIndex][colIndex].getCellContent() == CellContent.BOMB);
    }
}