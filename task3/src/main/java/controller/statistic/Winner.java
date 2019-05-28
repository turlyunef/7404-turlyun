package controller.statistic;

public class Winner {
    private String gameMode;
    private final int playTime;

    Winner(String gameMode, int playTime) {
        this.gameMode = gameMode;
        this.playTime = playTime;
    }

    public int getPlayTime() {
        return this.playTime;
    }

    public String getPlayMode() {
        return this.gameMode;
    }
}