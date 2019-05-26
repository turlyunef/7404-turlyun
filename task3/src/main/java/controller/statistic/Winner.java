package controller.statistic;

import model.game.GameProperties;

public class Winner {
    private String gameMode;
    private final int playTime;

    public Winner(String gameMode, int playTime) {
        this.gameMode = gameMode;
        this.playTime = playTime;
    }

    public int getPlayTime() {
        return playTime;
    }

    public String getPlayMode() {
        return this.gameMode;
    }
}
