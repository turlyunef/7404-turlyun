package controller.scoreboard;

import view.Constants;

import javax.swing.*;
import java.awt.*;

/**
 * Scoreboard with a given number of digits.
 */
class Scoreboard extends JPanel {
    private final int digitsCount;
    private final int[] numbers;

    /**
     * Creates scoreboard with a given number of digits.
     *
     * @param digitsCount given number of digits
     */
    Scoreboard(int digitsCount) {
        this.digitsCount = digitsCount;
        this.numbers = new int[digitsCount];
    }

    /**
     * Paints icons of numbers corresponding to numbers stored in the numbers array.
     *
     * @param g graphics object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < this.digitsCount; i++) {
            ImageIcon icon = getImageIconFromNumber(this.numbers[digitsCount - 1 - i]);
            g.drawImage(icon.getImage(), i * Constants.NUMBER_IMAGE_WIDTH, 0, this);
        }
    }

    /**
     * Returns image icon corresponding to the number.
     *
     * @param number number that needs a image icon
     * @return image icon corresponding to the number
     */
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

    /**
     * Sets the number on the scoreboard.
     *
     * @param number number that must be set on the scoreboard
     */
    public void setNumber(int number) {
        divideByDigits(number);
        initScoreboard();
    }

    /**
     * Divides the number into digits and write to the array numbers.
     *
     * @param number number that needs to be divided into digits
     */
    private void divideByDigits(int number) {
        for (int i = 0; i < this.numbers.length; i++) {
            this.numbers[i] = (int) (number / (Math.pow(10, i))) % 10;
        }
    }

    /**
     * Initializes scoreboard.
     */
    private void initScoreboard() {
        setPreferredSize(new Dimension(this.digitsCount * Constants.NUMBER_IMAGE_WIDTH, Constants.NUMBER_IMAGE_HEIGHT));
        repaint();
    }
}