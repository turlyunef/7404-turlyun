package model.game;

import view.Constants;

public class GameProperties {
    private int bombsCount;
    private int rows;
    private int cols;
    private String gameModeName;

    public GameProperties() {
        setProperties(Constants.DEFAULT_BOMBS_COUNT, Constants.DEFAULT_ROWS_COUNT, Constants.DEFAULT_COLUMNS_COUNT,
                Constants.DEFAULT_GAME_MODE_NAME);
    }

    public void setProperties(int bombsCount, int rows, int cols, String gameModeName) {
        this.bombsCount = bombsCount;
        this.rows = rows;
        this.cols = cols;
        this.gameModeName = gameModeName;
    }

    public int getBombsCount() {

        return bombsCount;
    }

    public int getRows() {

        return rows;
    }

    public int getCols() {

        return cols;
    }

    public String getGameModeName() {

        return gameModeName;
    }
}