package controller.cell;

import controller.Controllers;
import controller.Observed;
import controller.Observer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class ButtonMouseListener implements MouseListener, Observed {
    private final Controllers controllers;
    private final int rowIndex;
    private final int colIndex;
    private final ArrayList<Observer> observers = new ArrayList<>();


    public ButtonMouseListener(Controllers controllers, int rowIndex, int colIndex) {
        this.controllers = controllers;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        /*NOP*/
    }

    @Override
    public void mousePressed(MouseEvent e) {
        notifyObservers();
        if (e.getButton() == MouseEvent.BUTTON3) {
            this.controllers.pressedButton3(this.rowIndex, this.colIndex);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        notifyObservers();
        if (e.getButton() == MouseEvent.BUTTON1) {
            this.controllers.releasedButton1(this.rowIndex, this.colIndex);
        }
        if (e.getButton() == MouseEvent.BUTTON2) {
            this.controllers.releasedButton2(this.rowIndex, this.colIndex);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        /*NOP*/
    }

    @Override
    public void mouseExited(MouseEvent e) {
        /*NOP*/
    }

    @Override
    public void addObserver(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        this.observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : this.observers) {
            o.handleEvent();
        }
    }

    @Override
    public void notifyObservers(int number, String observerName) {
        /*NOP*/
    }
}