package controller.cell;

import model.game.field.outside.CellStatus;
import view.Constants;

import javax.swing.*;

public class ButtonCellController extends AbstractButtonCellController {
    private final JButton jButton;

    public ButtonCellController(JButton jButton) {
        super();
        this.jButton = jButton;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setExplodedMineCell() {
        Icon icon = new ImageIcon(Constants.class.getResource(Constants.EXPLODED_MINE_ICON));
        this.jButton.setIcon(icon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMineCell() {
        Icon icon = new ImageIcon(Constants.class.getResource(Constants.MINE_ICON));
        this.jButton.setIcon(icon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFlag() {
        cellStatus = CellStatus.FLAG;
        Icon icon = new ImageIcon(Constants.class.getResource(Constants.FLAG_ICON));
        this.jButton.setIcon(icon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setClosed() {
        cellStatus = CellStatus.CLOSE;
        Icon icon = new ImageIcon(Constants.class.getResource(Constants.CLOSED_ICON));
        this.jButton.setIcon(icon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFalseMinedCell() {
        Icon icon = new ImageIcon(Constants.class.getResource(Constants.NOT_MINE_ICON));
        this.jButton.setIcon(icon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOpenCell(int bombsAroundCellCount) {
        this.cellStatus = CellStatus.OPEN;
        drawBombsAroundCellCount(bombsAroundCellCount);
    }

    /**
     * Draws a button icon according to the number of bombs around it.
     *
     * @param bombsAroundCellCount number of bombs around cell.
     */
    private void drawBombsAroundCellCount(int bombsAroundCellCount) {
        switch (bombsAroundCellCount) {
            case 0: {
                Icon icon = new ImageIcon(Constants.class.getResource(Constants.NO_MINE_AROUND_ICON));
                this.jButton.setIcon(icon);
                break;
            }
            case 1: {
                Icon icon = new ImageIcon(Constants.class.getResource(Constants.ONE_MINE_AROUND_ICON));
                this.jButton.setIcon(icon);
                break;
            }
            case 2: {
                Icon icon = new ImageIcon(Constants.class.getResource(Constants.TWO_MINE_AROUND_ICON));
                this.jButton.setIcon(icon);
                break;
            }
            case 3: {
                Icon icon = new ImageIcon(Constants.class.getResource(Constants.THREE_MINE_AROUND_ICON));
                this.jButton.setIcon(icon);
                break;
            }
            case 4: {
                Icon icon = new ImageIcon(Constants.class.getResource(Constants.FOUR_MINE_AROUND_ICON));
                this.jButton.setIcon(icon);
                break;
            }
            case 5: {
                Icon icon = new ImageIcon(Constants.class.getResource(Constants.FIVE_MINE_AROUND_ICON));
                this.jButton.setIcon(icon);
                break;
            }
            case 6: {
                Icon icon = new ImageIcon(Constants.class.getResource(Constants.SIX_MINE_AROUND_ICON));
                this.jButton.setIcon(icon);
                break;
            }
            case 7: {
                Icon icon = new ImageIcon(Constants.class.getResource(Constants.SEVEN_MINE_AROUND_ICON));
                this.jButton.setIcon(icon);
                break;
            }
            case 8: {
                Icon icon = new ImageIcon(Constants.class.getResource(Constants.EIGHT_MINE_AROUND_ICON));
                this.jButton.setIcon(icon);
                break;
            }
        }
    }
}