package controller.scoreboard;

import controller.Observer;

public class TimerScoreboard extends Scoreboard implements Observer {
    public TimerScoreboard(int digitsCount) {
        super(digitsCount);
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