package controller;

import java.util.ArrayList;
import java.util.List;

public class GameTimer implements Runnable, Observed {
    private Thread timerThread;
    private boolean timerStoppedFlag = false;
    private int gameTime = 0;
    private List<Observer> observers = new ArrayList<>();

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

    public void runTimer() {
        if (this.timerThread == null) {
            this.timerThread = new Thread(this);
            this.timerThread.start();
        }
            this.timerStoppedFlag = false;
    }

    public void stopTimer() {
        this.timerStoppedFlag = true;
    }

    public void restartTimer(){
        this.gameTime = 0;
        this.timerStoppedFlag = true;
        this.timerThread = null;
        notifyObservers(this.gameTime, "timerPanel");
    }

    public int getTime(){

        return gameTime;
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.handleEvent();
        }
    }

    @Override
    public void notifyObservers(int number, String observerName) {
        for (Observer o : observers) {
            o.handleEvent(number, observerName);
        }
    }
}