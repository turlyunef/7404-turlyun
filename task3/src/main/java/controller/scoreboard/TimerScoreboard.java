package controller.scoreboard;

import controller.Observer;

/**
 * Game time scoreboard.
 */
public class TimerScoreboard extends Scoreboard implements Observer {
    public TimerScoreboard(int digitsCount) {
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
        if (observerName.equals("timerPanel")) {
            setNumber(number);
        }
    }
}