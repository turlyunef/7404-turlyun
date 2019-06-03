package model.game;

/**
 * Level of difficulty class, that contains parameters of the game mode.
 */
public enum DifficultyLevel {
    BEGINNER("Beginner", 10, 9, 9),
    EXPERIENCED("Experienced", 40, 16, 16),
    EXPERT("Expert", 99, 16, 30);

    private final String displayName;
    private final int bombsCount;
    private final int rows;
    private final int cols;

    /**
     * Constructor to create difficulty levels.
     *
     * @param displayName display name of the game mode
     * @param bombsCount  number of the bombs of the game mode
     * @param rows        number of the rows of the game field
     * @param cols        number of the columns of the game field
     */
    DifficultyLevel(String displayName, int bombsCount, int rows, int cols) {
        this.displayName = displayName;
        this.bombsCount = bombsCount;
        this.rows = rows;
        this.cols = cols;
    }

    /**
     * Returns the display name of the game mode.
     *
     * @return display name
     */
    public String getDisplayName() {

        return displayName;
    }

    /**
     * Returns the number of the bombs of the game mode.
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
     * Returns the number of the cols of the game field.
     *
     * @return number of the cols
     */
    public int getCols() {

        return cols;
    }
}