package controller.field;

import model.game.GameState;
import model.game.Model;
import model.game.field.cell.Cell;
import model.game.field.cell.CellContent;
import model.game.field.cell.CellStatus;
import view.CellButton;

import java.util.List;

/**
 * Game board class consisting of cell buttons controllers.
 */
public class ButtonGameFieldController implements GameFieldController {
    private final Model gameModel;
    private final CellButton[][] cellButtons;

    /**
     * Creates a field of controllers, creating a connection with the game model and a set of all field controllers.
     *
     * @param gameModel   game model
     * @param cellButtons set of all field controllers
     */
    public ButtonGameFieldController(Model gameModel, CellButton[][] cellButtons) {
        this.gameModel = gameModel;
        this.cellButtons = cellButtons;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tryOpenCell(int rowIndex, int colIndex) {
        if (checkGameNotEnded()) {
            List<Cell> openableCells = this.gameModel.getOpenedCellsByOpenCell(rowIndex, colIndex);
            for (Cell cell : openableCells) {
                openCell(cell);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void openCellsAround(int rowIndex, int colIndex) {
        List<Cell> openableCells = this.gameModel.getOpenedCellsByOpenCellsAround(rowIndex, colIndex);
        for (Cell cell : openableCells) {
            openCell(cell);
        }
    }

    /**
     * Opens a cell based on its content and status.
     *
     * @param cell open cell
     */
    private void openCell(Cell cell) {
        int rowIndex = cell.getRowIndex();
        int colIndex = cell.getColIndex();
        if (cell.getStatus() != CellStatus.FLAG) {
            switch (cell.getContent()) {
                case BOMB: {
                    if (cell.getStatus().equals(CellStatus.EXPLODED)) {
                        showBombs(rowIndex, colIndex);
                    }
                    break;
                }
                case EMPTY: {
                    int bombsAroundCellCount = cell.getBombsAroundCellCount();
                    this.cellButtons[rowIndex][colIndex].setOpenCell(bombsAroundCellCount);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeCellStatus(int rowIndex, int colIndex) {
        switch (this.gameModel.changeCellStatus(rowIndex, colIndex)) {
            case CLOSE: {
                this.cellButtons[rowIndex][colIndex].setClosed();
                break;
            }
            case FLAG: {
                this.cellButtons[rowIndex][colIndex].setFlag();
                break;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setController(CellButton cellButton, int rowIndex, int colIndex) {
        this.cellButtons[rowIndex][colIndex] = cellButton;
    }

    /**
     * Checks if the game is over.
     *
     * @return true if the game is not over
     */
    private boolean checkGameNotEnded() {

        return this.gameModel.getGameState().equals(GameState.PLAY);
    }

    /**
     * Shows information about all bombs.
     *
     * @param rowIndex explosive row index
     * @param colIndex explosive column index
     */
    private void showBombs(int rowIndex, int colIndex) {
        showAllNotExplodedBombs();
        showFalseMinedCell();
        this.cellButtons[rowIndex][colIndex].setExplodedMineCell();
    }

    /**
     * Shows all not exploded bombs on the game field.
     */
    private void showAllNotExplodedBombs() {
        List<Cell> bombs = this.gameModel.getAllBombs();
        for (Cell cell : bombs) {
            int rowIndex = cell.getRowIndex();
            int colIndex = cell.getColIndex();
            if (cell.getStatus().equals(CellStatus.CLOSE)) {
                this.cellButtons[rowIndex][colIndex].setMineCell();
            }
        }
    }

    /**
     * Shows the cells where the flags were placed incorrectly.
     */
    private void showFalseMinedCell() {
        List<Cell> flags = this.gameModel.getFlaggedCells();
        for (Cell cell : flags) {
            int rowIndex = cell.getRowIndex();
            int colIndex = cell.getColIndex();
            if (cell.getContent().equals(CellContent.EMPTY)) {
                this.cellButtons[rowIndex][colIndex].setFalseMinedCell();
            }
        }
    }
}