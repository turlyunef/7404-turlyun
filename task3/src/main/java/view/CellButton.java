package view;

import controller.field.ButtonMouseListener;

import javax.swing.*;
import java.awt.*;

/**
 * "Push" cell button of game field.
 */
public class CellButton extends JButton {
    private static final String EXPLODED_MINE_ICON_PATH = "/icons/mined.png";
    private static final String MINE_ICON_PATH = "/icons/mine.png";
    private static final String FLAG_ICON_PATH = "/icons/flag.png";
    private static final String NOT_MINE_ICON_PATH = "/icons/not_mine.png";
    private static final String CLOSED_ICON_PATH = "/icons/closed.png";

    private static final String NO_MINE_AROUND_CELL_ICON_PATH = "/icons/zero.png";
    private static final String ONE_MINE_AROUND_CELL_ICON_PATH = "/icons/one.png";
    private static final String TWO_MINE_AROUND_CELL_ICON_PATH = "/icons/two.png";
    private static final String THREE_MINE_AROUND_CELL_ICON_PATH = "/icons/three.png";
    private static final String FOUR_MINE_AROUND_CELL_ICON_PATH = "/icons/four.png";
    private static final String FIVE_MINE_AROUND_CELL_ICON_PATH = "/icons/five.png";
    private static final String SIX_MINE_AROUND_CELL_ICON_PATH = "/icons/six.png";
    private static final String SEVEN_MINE_AROUND_CELL_ICON_PATH = "/icons/seven.png";
    private static final String EIGHT_MINE_AROUND_CELL_ICON_PATH = "/icons/eight.png";

    private static final int GAME_CELL_IMAGE_HEIGHT = 40;
    private static final int GAME_CELL_IMAGE_WIDTH = 40;

    /**
     * Creates an initially closed cell of the playing cell based on a mouse listener.
     *
     * @param buttonMouseListener button listener playing field
     */
    CellButton(ButtonMouseListener buttonMouseListener) {
        addMouseListener(buttonMouseListener);
        Dimension buttonPreferredSize = new Dimension(GAME_CELL_IMAGE_WIDTH, GAME_CELL_IMAGE_HEIGHT);
        setPreferredSize(buttonPreferredSize);
        setClosed();
    }

    /**
     * Explodes a mine in the this cell.
     */
    public void setExplodedMineCell() {
        Icon icon = new ImageIcon(this.getClass().getResource(EXPLODED_MINE_ICON_PATH));
        setIcon(icon);
    }

    /**
     * Shows mine in this cell.
     */
    public void setMineCell() {
        Icon icon = new ImageIcon(this.getClass().getResource(MINE_ICON_PATH));
        setIcon(icon);
    }

    /**
     * Places flag on this cell.
     */
    public void setFlag() {
        Icon icon = new ImageIcon(this.getClass().getResource(FLAG_ICON_PATH));
        setIcon(icon);
    }

    /**
     * Sets to this cell status of the closed cell.
     */
    public void setClosed() {
        Icon icon = new ImageIcon(this.getClass().getResource(CLOSED_ICON_PATH));
        setIcon(icon);
    }

    /**
     * Shows on this cell that it is not a bomb and that the user mistakenly placed a flag on this cell.
     */
    public void setFalseMinedCell() {
        Icon icon = new ImageIcon(this.getClass().getResource(NOT_MINE_ICON_PATH));
        setIcon(icon);
    }

    /**
     * Sets to this cell status open.
     *
     * @param bombsAroundCellCount number of bombs around this cell.
     */
    public void setOpenCell(int bombsAroundCellCount) {
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
                Icon icon = new ImageIcon(this.getClass().getResource(NO_MINE_AROUND_CELL_ICON_PATH));
                setIcon(icon);
                break;
            }
            case 1: {
                Icon icon = new ImageIcon(this.getClass().getResource(ONE_MINE_AROUND_CELL_ICON_PATH));
                setIcon(icon);
                break;
            }
            case 2: {
                Icon icon = new ImageIcon(this.getClass().getResource(TWO_MINE_AROUND_CELL_ICON_PATH));
                setIcon(icon);
                break;
            }
            case 3: {
                Icon icon = new ImageIcon(this.getClass().getResource(THREE_MINE_AROUND_CELL_ICON_PATH));
                setIcon(icon);
                break;
            }
            case 4: {
                Icon icon = new ImageIcon(this.getClass().getResource(FOUR_MINE_AROUND_CELL_ICON_PATH));
                setIcon(icon);
                break;
            }
            case 5: {
                Icon icon = new ImageIcon(this.getClass().getResource(FIVE_MINE_AROUND_CELL_ICON_PATH));
                setIcon(icon);
                break;
            }
            case 6: {
                Icon icon = new ImageIcon(this.getClass().getResource(SIX_MINE_AROUND_CELL_ICON_PATH));
                setIcon(icon);
                break;
            }
            case 7: {
                Icon icon = new ImageIcon(this.getClass().getResource(SEVEN_MINE_AROUND_CELL_ICON_PATH));
                setIcon(icon);
                break;
            }
            case 8: {
                Icon icon = new ImageIcon(this.getClass().getResource(EIGHT_MINE_AROUND_CELL_ICON_PATH));
                setIcon(icon);
                break;
            }
        }
    }
}