package controller.event;

/**
 * Event changes counter bombs.
 */
public class BombsCounterChangeEvent implements Event {
    private final int bombsCounter;

    /**
     * The constructor of the event changes counter bombs.
     *
     * @param bombsCounter number of a bombs in cells around
     */
    public BombsCounterChangeEvent(int bombsCounter) {
        this.bombsCounter = bombsCounter;
    }

    /**
     * Returns the bombs counter.
     *
     * @return number of a bombs
     */
    public int getBombsCounter() {

        return bombsCounter;
    }
}