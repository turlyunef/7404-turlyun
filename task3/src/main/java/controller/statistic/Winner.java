package controller.statistic;

/**
 * Winner class with its results.
 */
public class Winner {
    private final String gameMode;
    private final int playTime;

    /**
     * Constructor creating a winner with his game mode and game time.
     *
     * @param gameMode game mode in which the user played
     * @param playTime game time
     */
    Winner(String gameMode, int playTime) {
        this.gameMode = gameMode;
        this.playTime = playTime;
    }

    /**
     * Returns the game time
     *
     * @return game time
     */
    public int getPlayTime() {
        return this.playTime;
    }

    /**
     * Returns the game mode in which the user played
     *
     * @return game mode
     */
    public String getPlayMode() {
        return this.gameMode;
    }
}