package controller.scoreboard;

import view.Constants;

import javax.swing.*;
import java.awt.*;

public class NumberPanel extends JPanel {
    private int charactersNumber;
    private static final int IMAGE_HEIGHT = 47;
    private static final int IMAGE_WIDTH = 38;
    private int[] numbers;

    public NumberPanel(int charactersNumber) {
        this.charactersNumber = charactersNumber;
        numbers = new int[charactersNumber];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < charactersNumber; i++) {
            ImageIcon icon = getImageIconFromNumber(numbers[charactersNumber-1 - i]);
            g.drawImage((Image) icon.getImage(), i * IMAGE_WIDTH, 0, this);
        }
    }

    private ImageIcon getImageIconFromNumber(int number) {
        switch (number){
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
        return null;
    }

    public void setNumber(int number) {
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = (int) (number / (Math.pow(10, i))) % 10;
        }
        initPanel();
    }

    private void initPanel() {
        setPreferredSize(new Dimension(charactersNumber * IMAGE_WIDTH, IMAGE_HEIGHT));
        repaint();
    }
}