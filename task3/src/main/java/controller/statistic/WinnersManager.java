package controller.statistic;

import model.game.GameProperties;

import java.util.ArrayList;
import java.util.List;

public class WinnersManager {
    private final List<Winner> winners = new ArrayList<>();

    public void createWinner(GameProperties gameProperties, int time) {
        winners.add(new Winner(gameProperties.getGameModeName(), time));
    }

    public List<Winner> getWinners() {

        return winners;
    }
}