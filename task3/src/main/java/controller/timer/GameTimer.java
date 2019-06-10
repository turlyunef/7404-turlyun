package controller.timer;

import controller.Observable;
import controller.event.Event;
import controller.event.TimerChangeEvent;
import model.game.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Timer updating the scoreboard with playing time. Executed in a separate thread.
 * Gets actual game time from game model
 */
public class GameTimer implements Runnable, Observable {
    private static final Logger log = LoggerFactory.getLogger(GameTimer.class);
    private volatile Thread timerThread;
    private volatile boolean timerStoppedFlag = false;
    private final List<Observer> observers = new ArrayList<>();
    private Model gameModel;

    /**
     * Sets the game model for communication with it to get the current game time.
     *
     * @param gameModel game model
     */
    public void setGameModel(Model gameModel) {
        this.gameModel = gameModel;
    }

    /**
     * Starts timer on the scoreboards.
     */
    @Override
    public void run() {
        while (!this.timerStoppedFlag) {
            try {
                updateTimerScoreboard();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                log.debug("Game timer was Interrupted.");
            }
            if (!this.timerStoppedFlag) {
                updateTimerScoreboard();
            }
        }
    }

    /**
     * Creates a new timer if it does not created or restores its work.
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
        this.timerStoppedFlag = true;
        if (this.timerThread != null) {
            this.timerThread.interrupt();
            this.timerThread = null;
        }
        updateTimerScoreboard();
    }

    /**
     * Updates the timer scoreboards value based on actual game time.
     */
    private void updateTimerScoreboard() {
        notifyObservers(new TimerChangeEvent((int) this.gameModel.getGameTime()));
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