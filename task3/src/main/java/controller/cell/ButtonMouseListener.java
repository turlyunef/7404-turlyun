package controller.cell;

import controller.Controllers;
import controller.Observable;
import controller.Observer;
import controller.event.Event;
import controller.event.MouseActionEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * The class of listener cell button playing field.
 */
public class ButtonMouseListener implements MouseListener, Observable {
    private final Controllers controllers;
    private final int rowIndex;
    private final int colIndex;
    private final ArrayList<Observer> observers = new ArrayList<>();

    /**
     * Creates button mouse listener.
     *
     * @param controllers link to all controllers
     * @param rowIndex    index on the rows of the cell
     * @param colIndex    index on the columns of the cell
     */
    public ButtonMouseListener(Controllers controllers, int rowIndex, int colIndex) {
        this.controllers = controllers;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        /*NOP*/
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mousePressed(MouseEvent e) {
        notifyObservers(new MouseActionEvent());
        if (e.getButton() == MouseEvent.BUTTON3) {
            this.controllers.pressedButton3(this.rowIndex, this.colIndex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        notifyObservers(new MouseActionEvent());
        if (e.getButton() == MouseEvent.BUTTON1) {
            this.controllers.releasedButton1(this.rowIndex, this.colIndex);
        }
        if (e.getButton() == MouseEvent.BUTTON2) {
            this.controllers.releasedButton2(this.rowIndex, this.colIndex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        /*NOP*/
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseExited(MouseEvent e) {
        /*NOP*/
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