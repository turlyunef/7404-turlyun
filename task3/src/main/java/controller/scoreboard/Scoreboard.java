package controller.scoreboard;

import view.Constants;

import javax.swing.*;
import java.awt.*;

public class Scoreboard extends JPanel {
    private int digitsCount;
    private int[] numbers;

    Scoreboard(int digitsCount) {
        this.digitsCount = digitsCount;
        this.numbers = new int[digitsCount];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < this.digitsCount; i++) {
            ImageIcon icon = getImageIconFromNumber(this.numbers[digitsCount - 1 - i]);
            g.drawImage(icon.getImage(), i * Constants.NUMBER_IMAGE_WIDTH, 0, this);
        }
    }

    private ImageIcon getImageIconFromNumber(int number) {
        switch (number) {
            case 0: {

                return new ImageIcon(Constants.class.getResource(Constants.ZERO_ICON));
            }
            case 1: {

                return new ImageIcon(Constants.class.getResource(Constants.ONE_ICON));
            }
            case 2: {

                return new ImageIcon(Constants.class.getResource(Constants.TWO_ICON));
            }
            case 3: {

                return new ImageIcon(Constants.class.getResource(Constants.THREE_ICON));
            }
            case 4: {

                return new ImageIcon(Constants.class.getResource(Constants.FOUR_ICON));
            }
            case 5: {

                return new ImageIcon(Constants.class.getResource(Constants.FIVE_ICON));
            }
            case 6: {

                return new ImageIcon(Constants.class.getResource(Constants.SIX_ICON));
            }
            case 7: {

                return new ImageIcon(Constants.class.getResource(Constants.SEVEN_ICON));
            }
            case 8: {

                return new ImageIcon(Constants.class.getResource(Constants.EIGHT_ICON));
            }
            case 9: {

                return new ImageIcon(Constants.class.getResource(Constants.NINE_ICON));
            }
        }

        return new ImageIcon(Constants.class.getResource(Constants.ZERO_ICON));
    }

    public void setNumber(int number) {
        for (int i = 0; i < this.numbers.length; i++) {
            this.numbers[i] = (int) (number / (Math.pow(10, i))) % 10;
        }
        initPanel();
    }

    private void initPanel() {
        setPreferredSize(new Dimension(this.digitsCount * Constants.NUMBER_IMAGE_WIDTH, Constants.NUMBER_IMAGE_HEIGHT));
        repaint();
    }
}