package controller.scoreboard;

import controller.Observer;

public class TimerNumberPanel extends NumberPanel implements Observer {
    public TimerNumberPanel(int charactersNumber) {
        super(charactersNumber);
    }

    @Override
    public void handleEvent() {
        /*NOP*/
    }

    @Override
    public void handleEvent(int number, String observerName) {
        if (observerName.equals("timerPanel")){
            setNumber(number);
        }
    }
}