package view.scoreboard;

import controller.event.BombsCounterChangeEvent;
import controller.event.Event;
import view.Observer;

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
    public void handleEvent(Event event) {
        if (event instanceof BombsCounterChangeEvent) {
            setNumber(((BombsCounterChangeEvent) event).getBombsCounter());
        }
    }
}