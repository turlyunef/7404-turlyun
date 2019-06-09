package controller.timer;

import controller.Observable;
import view.Observer;
import controller.event.Event;
import controller.event.TimerChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Game play timer. Executed in a separate thread.
 */
public class GameTimer implements Runnable, Observable {
    private static final Logger log = LoggerFactory.getLogger(GameTimer.class);
    private volatile Thread timerThread;
    private volatile boolean timerStoppedFlag = false;
    private volatile int gameTime = 0;
    private volatile List<Observer> observers = new ArrayList<>();

    /**
     * Runs timer.
     */
    @Override
    public void run() {
        while (!this.timerStoppedFlag) {
            try {
                updateTimerScoreboard();
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                log.debug("Game timer was Interrupted.");
            }
            if (!this.timerStoppedFlag) {
                this.gameTime++;
                updateTimerScoreboard();
            }
        }
    }

    /**
     * Creates a new timer if it is not created or restores its work.
     */
    public void runTimer() {
        if (this.timerThread == null) {
            this.timerThread = new Thread(this);
            this.timerThread.start();
        }
        this.timerStoppedFlag = false;
    }

    /**
     * Stops the timer.
     */
    public void stopTimer() {
        this.timerStoppedFlag = true;
    }

    /**
     * Restarts the timer.
     */
    public void restartTimer() {
        this.gameTime = 0;
        this.timerStoppedFlag = true;
        if (this.timerThread != null) {
            this.timerThread.interrupt();
            this.timerThread = null;
            System.out.println(this.timerThread);
        }
        updateTimerScoreboard();
    }

    /**
     * Updates the timer scoreboards value based on actual game time.
     */
    private void updateTimerScoreboard() {
        notifyObservers(new TimerChangeEvent(this.gameTime));
    }

    /**
     * Returns the current game time.
     *
     * @return current game time
     */
    public int getTime() {

        return this.gameTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addObserver(Observer o) {
        this.observers.add(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObserver(Observer o) {
        this.observers.remove(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyObservers(Event event) {
        for (Observer o : this.observers) {
            o.handleEvent(event);
        }
    }
}