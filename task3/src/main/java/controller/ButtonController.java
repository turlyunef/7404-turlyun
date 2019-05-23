package controller;

import view.Constants;

import javax.swing.*;

public class ButtonController extends AbstractController {
    private JButton jButton;

    public ButtonController(JButton jButton) {
        super();
        this.jButton = jButton;
    }

    @Override
    void setExplodedMineCell() {
        Icon minedIcon = new ImageIcon(Constants.class.getResource(Constants.EXPLODED_MINE_ICON));
        jButton.setIcon(minedIcon);
    }

    @Override
    void setMineCell() {
        Icon mineIcon = new ImageIcon(Constants.class.getResource(Constants.MINE_ICON));
        jButton.setIcon(mineIcon);
    }

    @Override
    void setFlag() {
        cellStatus = CellStatus.FLAG;
        Icon flagIcon = new ImageIcon(Constants.class.getResource(Constants.FLAG_ICON));
        jButton.setIcon(flagIcon);
    }

    @Override
    void setClose() {
        cellStatus = CellStatus.CLOSE;
        Icon closedIcon = new ImageIcon(Constants.class.getResource(Constants.CLOSED_ICON));
        jButton.setIcon(closedIcon);
    }

    @Override
    void setFalseMinedCell() {
        Icon falseMinedIcon = new ImageIcon(Constants.class.getResource(Constants.NOT_MINE_ICON));
        jButton.setIcon(falseMinedIcon);
    }

    @Override
    void setOpenCell(int bombsAroundCellCount) {
        cellStatus = CellStatus.OPEN;
        drawBombsAroundCellCount(bombsAroundCellCount);
    }

    private void drawBombsAroundCellCount(int bombsAroundCellCount) {
        switch (bombsAroundCellCount) {
            case 0: {
                Icon openIcon = new ImageIcon(Constants.class.getResource(Constants.NO_MINE_AROUND_ICON));
                jButton.setIcon(openIcon);
                break;
            }
            case 1: {
                Icon openIcon = new ImageIcon(Constants.class.getResource(Constants.ONE_MINE_AROUND_ICON));
                jButton.setIcon(openIcon);
                break;
            }
            case 2: {
                Icon openIcon = new ImageIcon(Constants.class.getResource(Constants.TWO_MINE_AROUND_ICON));
                jButton.setIcon(openIcon);
                break;
            }
            case 3: {
                Icon openIcon = new ImageIcon(Constants.class.getResource(Constants.THREE_MINE_AROUND_ICON));
                jButton.setIcon(openIcon);
                break;
            }
            case 4: {
                Icon openIcon = new ImageIcon(Constants.class.getResource(Constants.FOUR_MINE_AROUND_ICON));
                jButton.setIcon(openIcon);
                break;
            }
            case 5: {
                Icon openIcon = new ImageIcon(Constants.class.getResource(Constants.FIVE_MINE_AROUND_ICON));
                jButton.setIcon(openIcon);
                break;
            }
            case 6: {
                Icon openIcon = new ImageIcon(Constants.class.getResource(Constants.SIX_MINE_AROUND_ICON));
                jButton.setIcon(openIcon);
                break;
            }
            case 7: {
                Icon openIcon = new ImageIcon(Constants.class.getResource(Constants.SEVEN_MINE_AROUND_ICON));
                jButton.setIcon(openIcon);
                break;
            }
            case 8: {
                Icon openIcon = new ImageIcon(Constants.class.getResource(Constants.EIGHT_MINE_AROUND_ICON));
                jButton.setIcon(openIcon);
                break;
            }
        }
    }
}