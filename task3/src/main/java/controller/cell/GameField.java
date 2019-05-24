package controller.cell;

import model.game.GameState;

public interface GameField {
    void tryOpenCell(int rowIndex, int columnIndex);

    void setController(ButtonController buttonController, int rowIndex, int columnIndex);

    void openCellsAround(int rowIndex, int colIndex);

    void changeCellStatus(int rowIndex, int columnIndex);

    GameState getGameState();
}
