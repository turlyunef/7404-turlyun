package controller.scoreboard;

import controller.Observer;

public class BombsCounterNumberPanel extends NumberPanel implements Observer {
    public BombsCounterNumberPanel(int charactersNumber) {
        super(charactersNumber);
    }

    @Override
    public void handleEvent() {
        /*NOP*/
    }

    @Override
    public void handleEvent(int number, String observerName) {
        if (observerName.equals("bombsCounterPanel")){
            setNumber(number);

        }
    }
}
