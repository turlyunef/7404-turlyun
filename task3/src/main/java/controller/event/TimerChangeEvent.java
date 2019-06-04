package controller.event;

/**
 * Timer value change event.
 */
public class TimerChangeEvent implements Event {
    private final int gameTime;

    /**
     * Timer value change event creation constructor.
     *
     * @param gameTime value of the timer
     */
    public TimerChangeEvent(int gameTime) {
        this.gameTime = gameTime;
    }

    /**
     * Returns the timer value.
     *
     * @return value of the timer
     */
    public int getGameTime() {
        return gameTime;
    }
}