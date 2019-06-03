package controller.cell;

import model.game.GameState;
import model.game.IModel;
import model.game.field.cell.CellStatus;

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
            if (this.gameModel.getCell(rowIndex, colIndex).isBomb()) {
                explode(rowIndex, colIndex);
            } else {
                int bombsAroundCellCount = this.gameModel.getCell(rowIndex, colIndex).getBombsAroundCellCount();
                this.cellControllers[rowIndex][colIndex].setOpenCell(bombsAroundCellCount);
                if (bombsAroundCellCount == 0) {
                    openCellsAround(rowIndex, colIndex);
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
                changeDefusedBombsCountersInCellsAround(rowIndex, colIndex, -1);
                this.cellControllers[rowIndex][colIndex].setClosed();
                break;
            }
            case FLAG: {
                changeDefusedBombsCountersInCellsAround(rowIndex, colIndex, 1);
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
        if ((!cellIsClose(rowIndex, colIndex)) && (cellsAroundIsDemined(rowIndex, colIndex))) {
            for (int i = rowIndex - 1; i <= rowIndex + 1; i++) {
                for (int j = colIndex - 1; j <= colIndex + 1; j++) {
                    if (isCellExist(i, j)) {
                        tryOpenCell(i, j);
                    }
                }
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
        for (int i = 0; i < this.cellControllers.length; i++) {
            for (int j = 0; j < this.cellControllers[i].length; j++) {
                if (this.gameModel.getCell(i, j).isBomb()) {
                    if (this.cellControllers[i][j].getCellStatus().equals(CellStatus.CLOSE)) {
                        this.cellControllers[i][j].setMineCell();
                    }
                } else {
                    if (this.cellControllers[i][j].getCellStatus().equals(CellStatus.FLAG)) {
                        this.cellControllers[i][j].setFalseMinedCell();
                    }
                }
            }
        }
    }

    /**
     * Changes the number of cleared bombs in cells around the given cell.
     *
     * @param rowIndex index on the rows of the given cell
     * @param colIndex index on the columns of the given cell
     * @param value    value by which the number of defused bombs changes
     */
    private void changeDefusedBombsCountersInCellsAround(int rowIndex, int colIndex, int value) {
        for (int i = rowIndex - 1; i <= rowIndex + 1; i++) {
            for (int j = colIndex - 1; j <= colIndex + 1; j++) {
                if (isCellExist(i, j)) {
                    this.cellControllers[i][j].changeFlaggedBombsCounter(value);
                }
            }
        }
    }

    /**
     * Checks if a given cell is within the playing field.
     *
     * @param rowIndex index on the rows of the given cell
     * @param colIndex index on the columns of the given cell
     * @return true if the cell comes within the playing field
     */
    private boolean isCellExist(int rowIndex, int colIndex) {

        return !((rowIndex < 0) | (colIndex < 0) |
                (rowIndex >= this.cellControllers.length) |
                (colIndex >= this.cellControllers[0].length));
    }

    /**
     * Checks if all cells are cleared around a given cell.
     *
     * @param rowIndex index on the rows of the given cell
     * @param colIndex index on the columns of the given cell
     * @return true if all the cells around a given cell are cleared
     */
    private boolean cellsAroundIsDemined(int rowIndex, int colIndex) {

        return this.cellControllers[rowIndex][colIndex].getFlaggedBombsCounter() >=
                this.gameModel.getCell(rowIndex, colIndex).getBombsAroundCellCount();
    }
}