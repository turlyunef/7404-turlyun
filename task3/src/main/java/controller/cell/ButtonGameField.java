package controller.cell;

import model.game.GameState;
import model.game.IModel;
import model.game.field.cell.Cell;
import model.game.field.cell.CellStatus;

import java.util.List;

public class ButtonGameField implements GameField {

    private final IModel gameModel;
    private final AbstractButtonCellController[][] cellControllers;

    /**
     * Creates a field of controllers, creating a connection with the game model and a set of all field controllers.
     *
     * @param gameModel       game model
     * @param cellControllers set of all field controllers
     */
    public ButtonGameField(IModel gameModel, AbstractButtonCellController[][] cellControllers) {
        this.gameModel = gameModel;
        this.cellControllers = cellControllers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tryOpenCell(int rowIndex, int colIndex) {
        if (checkGameNotEnded() && (cellIsClose(rowIndex, colIndex))) {
            List<Cell> openableCells = this.gameModel.getOpenedCellsByOpenCell(rowIndex, colIndex);
            for (Cell cell : openableCells) {
                openCell(cell);
            }
        }
    }

    /**
     * TODO
     *
     * @param cell
     */
    private void openCell(Cell cell) {
        int rowIndex = cell.getRowIndex();
        int colIndex = cell.getColIndex();
        if (cell.getStatus() != CellStatus.FLAG) {
            switch (cell.getContent()) {
                case BOMB: {
                    if (cell.getStatus().equals(CellStatus.EXPLODED)) {
                        explode(rowIndex, colIndex);
                    }
                    break;
                }
                case EMPTY: {
                    int bombsAroundCellCount = cell.getBombsAroundCellCount();
                    this.cellControllers[rowIndex][colIndex].setOpenCell(bombsAroundCellCount);
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
                this.cellControllers[rowIndex][colIndex].setClosed();
                break;
            }
            case FLAG: {
                this.cellControllers[rowIndex][colIndex].setFlag();
                break;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameState getGameState() {

        return this.gameModel.getGameState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void openCellsAround(int rowIndex, int colIndex) {
        if (!cellIsClose(rowIndex, colIndex)) {
            List<Cell> openableCells = this.gameModel.getOpenedCellsByOpenCellsAround(rowIndex, colIndex);
            for (Cell cell : openableCells) {
                openCell(cell);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setController(ButtonCellController buttonCellController, int rowIndex, int colIndex) {
        this.cellControllers[rowIndex][colIndex] = buttonCellController;
    }

    /**
     * Check if the game is over.
     *
     * @return true if the game is not over
     */
    private boolean checkGameNotEnded() {

        return (this.gameModel.getGameState().equals(GameState.PLAY));
    }

    /**
     * Explodes the cell.
     *
     * @param rowIndex index on the rows of the cell
     * @param colIndex index on the columns of the cell
     */
    private void explode(int rowIndex, int colIndex) {
        showAllBombs();
        this.cellControllers[rowIndex][colIndex].setExplodedMineCell();
    }

    /**
     * Check if the cell is closed.
     *
     * @param rowIndex index on the rows of the cell
     * @param colIndex index on the columns of the cell
     * @return true if the cell is closed
     */
    private boolean cellIsClose(int rowIndex, int colIndex) {

        return this.cellControllers[rowIndex][colIndex].getCellStatus().equals(CellStatus.CLOSE);
    }

    /**
     * Shows all bombs on the game field.
     */
    private void showAllBombs() {
        List<Cell> bombs = this.gameModel.getAllBombs();
        for (Cell cell : bombs) {
            int rowIndex = cell.getRowIndex();
            int colIndex = cell.getColIndex();
            if (this.cellControllers[rowIndex][colIndex].getCellStatus().equals(CellStatus.CLOSE)) {
                this.cellControllers[rowIndex][colIndex].setMineCell();
            }
        }
    }

//    private void showFalseMine(){
//        if {
//            if (this.cellControllers[rowIndex][colIndex].getCellStatus().equals(CellStatus.FLAG)) {
//                this.cellControllers[rowIndex][colIndex].setFalseMinedCell();
//            }
//        }
//    }
}