package controller.statistic;

import model.game.GameProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * The winners manager.
 */
public class WinnersManager {
    private final List<Winner> winners = new ArrayList<>();

    /**
     * Adds the winner with its results to the list of winners.
     *
     * @param gameProperties game properties containing number of bombs of the game, number of rows and columns game field
     * @param time           game time
     */
    public void addWinner(GameProperties gameProperties, int time) {
        winners.add(new Winner(gameProperties.getGameModeName(), time));
    }

    /**
     * Returns the list of winners.
     *
     * @return winners
     */
    public List<Winner> getWinners() {

        return winners;
    }
}