package controller;

import model.game.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.Constants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class Controller implements ActionListener, Observer, Observed, MouseListener {
    private static Logger log = LoggerFactory.getLogger(Controller.class);

    private Model model;
    private JButton jButton;
    private int rowIndex;
    private int columnIndex;
    private CellStatus cellStatus = CellStatus.CLOSE;

    public Controller(int rowIndex, int columnIndex, JButton jButton, Model model) {
        super();
        this.model = model;
        this.jButton = jButton;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.cellStatus != CellStatus.OPEN) {
            openCell();
        }
    }

    @Override
    public void handleEvent() {
        if (this.cellStatus.equals(CellStatus.CLOSE)) {
            setOpenCell();
        }
    }

    private void openCell() {
        if (model.cellIsBomb(this.rowIndex, this.columnIndex)) {
            setMinedCell();
        } else {
            setOpenCell();
        }
    }

    private void setMinedCell() {
        Icon minedIcon = new ImageIcon(Constants.class.getResource(Constants.MINE_ICON));
        jButton.setIcon(minedIcon);
    }

    private void setOpenCell() {
        this.setCellStatus(CellStatus.OPEN);
        if (model.getCell(this.rowIndex, this.columnIndex).getBombsAroundCellCount() == 0) {
            Icon openIcon = new ImageIcon(Constants.class.getResource(Constants.NO_MINE_AROUND_ICON));
            jButton.setIcon(openIcon);
            notifyObservers();
        } else if (model.getCell(this.rowIndex, this.columnIndex).getBombsAroundCellCount() == 1) {
            Icon openIcon = new ImageIcon(Constants.class.getResource(Constants.ONE_MINE_AROUND_ICON));
            jButton.setIcon(openIcon);
        } else if (model.getCell(this.rowIndex, this.columnIndex).getBombsAroundCellCount() == 2) {
            Icon openIcon = new ImageIcon(Constants.class.getResource(Constants.TWO_MINE_AROUND_ICON));
            jButton.setIcon(openIcon);
        } else if (model.getCell(this.rowIndex, this.columnIndex).getBombsAroundCellCount() == 3) {
            Icon openIcon = new ImageIcon(Constants.class.getResource(Constants.THREE_MINE_AROUND_ICON));
            jButton.setIcon(openIcon);
        } else if (model.getCell(this.rowIndex, this.columnIndex).getBombsAroundCellCount() == 4) {
            Icon openIcon = new ImageIcon(Constants.class.getResource(Constants.FOUR_MINE_AROUND_ICON));
            jButton.setIcon(openIcon);
        } else if (model.getCell(this.rowIndex, this.columnIndex).getBombsAroundCellCount() == 5) {
            Icon openIcon = new ImageIcon(Constants.class.getResource(Constants.FIVE_MINE_AROUND_ICON));
            jButton.setIcon(openIcon);
        } else if (model.getCell(this.rowIndex, this.columnIndex).getBombsAroundCellCount() == 6) {
            Icon openIcon = new ImageIcon(Constants.class.getResource(Constants.SIX_MINE_AROUND_ICON));
            jButton.setIcon(openIcon);
        } else if (model.getCell(this.rowIndex, this.columnIndex).getBombsAroundCellCount() == 7) {
            Icon openIcon = new ImageIcon(Constants.class.getResource(Constants.SEVEN_MINE_AROUND_ICON));
            jButton.setIcon(openIcon);
        } else if (model.getCell(this.rowIndex, this.columnIndex).getBombsAroundCellCount() == 8) {
            Icon openIcon = new ImageIcon(Constants.class.getResource(Constants.EIGHT_MINE_AROUND_ICON));
            jButton.setIcon(openIcon);
        }
    }

    private List<Observer> neighboringButtons = new ArrayList<>();

    @Override
    public void addObserver(Observer o) {
        neighboringButtons.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        neighboringButtons.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : neighboringButtons
        ) {
            o.handleEvent();
        }
    }

    public CellStatus getCellStatus() {
        return cellStatus;
    }

    public void setCellStatus(CellStatus cellStatus) {
        this.cellStatus = cellStatus;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton()==MouseEvent.BUTTON3){
            if (this.cellStatus.equals(CellStatus.CLOSE)) {
                setCellStatus(CellStatus.FLAG);
                Icon flagIcon = new ImageIcon(Constants.class.getResource(Constants.FLAG_ICON));
                jButton.setIcon(flagIcon);
            }
            else if (this.cellStatus.equals(CellStatus.FLAG)) {
                setCellStatus(CellStatus.QUESTION);
                Icon questionIcon = new ImageIcon(Constants.class.getResource(Constants.QUESTION_ICON));
                jButton.setIcon(questionIcon);
            }
            else if (this.cellStatus.equals(CellStatus.QUESTION)) {
                setCellStatus(CellStatus.CLOSE);
                Icon closedIcon = new ImageIcon(Constants.class.getResource(Constants.CLOSED_ICON));
                jButton.setIcon(closedIcon);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}