package view;

import controller.Controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonMouseListener implements MouseListener {
    private Controllers controllers;
    private int rowIndex;
    private int columnIndex;


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
        if (e.getButton() == MouseEvent.BUTTON3) {
            controllers.pressedButton3(rowIndex, columnIndex);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
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
}