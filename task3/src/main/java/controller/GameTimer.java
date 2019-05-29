package controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Game play timer. Executed in a separate thread.
 */
public class GameTimer implements Runnable, Observed {
    private Thread timerThread;
    private boolean timerStoppedFlag = false;
    private int gameTime = 0;
    private final List<Observer> observers = new ArrayList<>();

    /**
     * Runs timer.
     */
    @Override
    public void run() {
        while (!this.timerStoppedFlag) {
            try {
                notifyObservers(this.gameTime, "timerPanel");
                Thread.sleep(1000);
                this.gameTime++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates a new timer if it is not created or restores its work.
     */
    void runTimer() {
        if (this.timerThread == null) {
            this.timerThread = new Thread(this);
            this.timerThread.start();
        }
        this.timerStoppedFlag = false;
    }

    /**
     * Stops the timer.
     */
    void stopTimer() {
        this.timerStoppedFlag = true;
    }

    /**
     * Restarts the timer.
     */
    void restartTimer() {
        this.gameTime = 0;
        this.timerStoppedFlag = true;
        this.timerThread = null;
        notifyObservers(this.gameTime, "timerPanel");
    }

    /**
     * Returns the current game time.
     *
     * @return current game time
     */
    int getTime() {

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
    public void notifyObservers() {
        for (Observer o : this.observers) {
            o.handleEvent();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyObservers(int number, String observerName) {
        for (Observer o : this.observers) {
            o.handleEvent(number, observerName);
        }
    }
}