package controller.statistic;

import model.game.GameProperties;

import java.util.ArrayList;
import java.util.List;

public class WinnersManager {
    private long startTime = 0;
    private final List<Winner> winners = new ArrayList<>();

    public void startTimer() {
        if (this.startTime == 0) {
            this.startTime = System.currentTimeMillis();
        }
    }

    public void createWinner(GameProperties gameProperties) {
        winners.add(new Winner(gameProperties.getGameModeName(), getPlayTime()));
        this.startTime = 0;
    }

    private int getPlayTime() {

        return (int) (System.currentTimeMillis() - this.startTime) / 1000;
    }

    public List<Winner> getWinners() {

        return winners;
    }
}
