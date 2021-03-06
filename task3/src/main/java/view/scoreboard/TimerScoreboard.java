package view.scoreboard;

import controller.event.Event;
import controller.event.TimerChangeEvent;
import view.Observer;

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
    public void handleEvent(Event event) {
        if (event instanceof TimerChangeEvent) {
            setNumber(((TimerChangeEvent) event).getGameTime());
        }
    }
}