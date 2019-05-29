package controller.scoreboard;

import controller.Observer;

/**
 * The class of the board displaying the number of bombs not cleared by the flag by the user.
 */
public class BombsCounterScoreboard extends Scoreboard implements Observer {
    public BombsCounterScoreboard(int digitsCount) {
        super(digitsCount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleEvent() {
        /*NOP*/
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleEvent(int number, String observerName) {
        if (observerName.equals("bombsCounterPanel")) {
            setNumber(number);
        }
    }
}