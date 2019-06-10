package controller.event;

/**
 * Timer value change event.
 */
public class TimerChangeEvent implements Event {
    private final int gameTime;

    /**
     * The constructor of the timer value change event.
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

        return this.gameTime;
    }
}