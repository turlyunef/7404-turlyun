package controller.event;

/**
 * Bomb counter change event.
 */
public class BombsCounterChangeEvent implements Event {
    private final int bombsCounter;

    /**
     * Bomb counter change event creation constructor.
     *
     * @param bombsCounter number of a bombs
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