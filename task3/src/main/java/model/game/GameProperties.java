package model.game;

import view.Constants;

/**
 * Game properties containing number of bombs of the game, number of rows and columns game field and the name of the game mode.
 */
public class GameProperties {
    private int bombsCount;
    private int rows;
    private int cols;
    private String gameModeName;

    /**
     * Constructor to create a game properties with the default settings
     */
    public GameProperties() {
        setProperties(Constants.DEFAULT_BOMBS_COUNT, Constants.DEFAULT_ROWS_COUNT, Constants.DEFAULT_COLUMNS_COUNT,
                Constants.DEFAULT_GAME_MODE_NAME);
    }

    /**
     * Sets game settings to the properties
     *
     * @param bombsCount   number of the bombs of the game
     * @param rows         number of the rows of the game field
     * @param cols         number of the columns of the game field
     * @param gameModeName name of the game mode
     */
    public void setProperties(int bombsCount, int rows, int cols, String gameModeName) {
        this.bombsCount = bombsCount;
        this.rows = rows;
        this.cols = cols;
        this.gameModeName = gameModeName;
    }

    /**
     * Returns the number of the bombs of the game
     *
     * @return number of the bombs
     */
    public int getBombsCount() {

        return bombsCount;
    }

    /**
     * Returns the number of the rows of the game field
     *
     * @return number of the rows
     */
    public int getRows() {

        return rows;
    }

    /**
     * Returns the number of the columns of the game field
     *
     * @return number of the columns
     */
    public int getCols() {

        return cols;
    }

    /**
     * Returns the name of the game mode
     *
     * @return name of the game mode
     */
    public String getGameModeName() {

        return gameModeName;
    }
}