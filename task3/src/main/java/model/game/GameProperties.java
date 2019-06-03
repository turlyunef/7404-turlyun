package model.game;

/**
 * Game properties containing number of bombs of the game, number of rows and columns game field and the name of the game mode.
 */
public class GameProperties {
    private int bombsCount;
    private int rows;
    private int cols;
    private String gameModeName;
    private static final DifficultyLevel DEFAULT_DIFFICULT_LEVEL = DifficultyLevel.BEGINNER;

    /**
     * Constructor to create a game properties with the default settings.
     */
    public GameProperties() {
        setProperties(DEFAULT_DIFFICULT_LEVEL);
    }

    /**
     * Sets game settings to the properties.
     *
     * @param difficultyLevel level of difficulty, that contains parameters of the game mode
     */
    public void setProperties(DifficultyLevel difficultyLevel) {
        this.bombsCount = difficultyLevel.getBombsCount();
        this.rows = difficultyLevel.getRows();
        this.cols = difficultyLevel.getCols();
        this.gameModeName = difficultyLevel.getDisplayName();
    }

    /**
     * Sets game settings to the properties.
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
     * Returns the number of the bombs of the game.
     *
     * @return number of the bombs
     */
    public int getBombsCount() {

        return bombsCount;
    }

    /**
     * Returns the number of the rows of the game field.
     *
     * @return number of the rows
     */
    public int getRows() {

        return rows;
    }

    /**
     * Returns the number of the columns of the game field.
     *
     * @return number of the columns
     */
    public int getCols() {

        return cols;
    }

    /**
     * Returns the name of the game mode.
     *
     * @return name of the game mode
     */
    public String getGameModeName() {

        return gameModeName;
    }
}