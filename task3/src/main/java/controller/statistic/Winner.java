package controller.statistic;

import model.game.GameProperties;

public class Winner {
    private GameProperties gameProperties;
    private int playTime;

    public Winner(GameProperties gameProperties, int playTime) {
        this.gameProperties = gameProperties;
        this.playTime = playTime;
    }

    public GameProperties getGameProperties() {
        return gameProperties;
    }

    public int getPlayTime() {
        return playTime;
    }

    public String getPlayMode() {
        return gameProperties.getGameModeName();
    }
}
