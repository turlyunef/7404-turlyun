package controller.scoreboard;

import controller.Observer;

public class BombsCounterScoreboard extends Scoreboard implements Observer {
    public BombsCounterScoreboard(int digitsCount) {
        super(digitsCount);
    }

    @Override
    public void handleEvent() {
        /*NOP*/
    }

    @Override
    public void handleEvent(int number, String observerName) {
        if (observerName.equals("bombsCounterPanel")) {
            setNumber(number);
        }
    }
}