package model.game.statistic;

/**
 * Winner class with its results.
 */
public class Winner {
    private final String gameMode;
    private final float playTime;

    /**
     * Constructor creating a winner with his game mode and game time.
     *
     * @param gameMode game mode in which the user played
     * @param playTime game time
     */
    Winner(String gameMode, float playTime) {
        this.gameMode = gameMode;
        this.playTime = playTime;
    }

    /**
     * Returns the game time.
     *
     * @return game time
     */
    public float getPlayTime() {

        return this.playTime;
    }

    /**
     * Returns the game mode in which the user played.
     *
     * @return game mode
     */
    public String getPlayMode() {

        return this.gameMode;
    }
}