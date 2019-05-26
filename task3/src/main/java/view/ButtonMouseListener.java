package view;

import controller.Controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class ButtonMouseListener implements MouseListener, Observed {
    private final Controllers controllers;
    private final int rowIndex;
    private final int columnIndex;
    private final ArrayList<Observer> observers = new ArrayList<>();


    ButtonMouseListener(Controllers controllers, int rowIndex, int columnIndex) {
        this.controllers = controllers;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        notifyObservers();
        if (e.getButton() == MouseEvent.BUTTON3) {
            controllers.pressedButton3(rowIndex, columnIndex);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        notifyObservers();
        if (e.getButton() == MouseEvent.BUTTON1) {
            controllers.releasedButton1(rowIndex, columnIndex);
        }
        if (e.getButton() == MouseEvent.BUTTON2) {
            controllers.releasedButton2(rowIndex, columnIndex);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

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
        for (Observer o :
                observers) {
            o.handleEvent();
        }
    }
}