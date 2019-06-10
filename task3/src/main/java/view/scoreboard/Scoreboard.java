package view.scoreboard;

import javax.swing.*;
import java.awt.*;

/**
 * Scoreboard with a given number of digits.
 */
class Scoreboard extends JPanel {
    private static final String ZERO_NUMBER_ICON = "/numbers/0.png";
    private static final String ONE_NUMBER_ICON = "/numbers/1.png";
    private static final String TWO_NUMBER_ICON = "/numbers/2.png";
    private static final String THREE_NUMBER_ICON = "/numbers/3.png";
    private static final String FOUR_NUMBER_ICON = "/numbers/4.png";
    private static final String FIVE_NUMBER_ICON = "/numbers/5.png";
    private static final String SIX_NUMBER_ICON = "/numbers/6.png";
    private static final String SEVEN_NUMBER_ICON = "/numbers/7.png";
    private static final String EIGHT_NUMBER_ICON = "/numbers/8.png";
    private static final String NINE_NUMBER_ICON = "/numbers/9.png";
    private static final int NUMBER_ICON_HEIGHT = 47;
    private static final int NUMBER_ICON_WIDTH = 38;

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
            g.drawImage(icon.getImage(), i * NUMBER_ICON_WIDTH, 0, this);
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

                return new ImageIcon(this.getClass().getResource(ZERO_NUMBER_ICON));
            }
            case 1: {

                return new ImageIcon(this.getClass().getResource(ONE_NUMBER_ICON));
            }
            case 2: {

                return new ImageIcon(this.getClass().getResource(TWO_NUMBER_ICON));
            }
            case 3: {

                return new ImageIcon(this.getClass().getResource(THREE_NUMBER_ICON));
            }
            case 4: {

                return new ImageIcon(this.getClass().getResource(FOUR_NUMBER_ICON));
            }
            case 5: {

                return new ImageIcon(this.getClass().getResource(FIVE_NUMBER_ICON));
            }
            case 6: {

                return new ImageIcon(this.getClass().getResource(SIX_NUMBER_ICON));
            }
            case 7: {

                return new ImageIcon(this.getClass().getResource(SEVEN_NUMBER_ICON));
            }
            case 8: {

                return new ImageIcon(this.getClass().getResource(EIGHT_NUMBER_ICON));
            }
            case 9: {

                return new ImageIcon(this.getClass().getResource(NINE_NUMBER_ICON));
            }
        }

        return new ImageIcon(this.getClass().getResource(ZERO_NUMBER_ICON));
    }

    /**
     * Sets the number on the scoreboard.
     *
     * @param number number that must be set on the scoreboard
     */
    void setNumber(int number) {
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
        setPreferredSize(new Dimension(this.digitsCount * NUMBER_ICON_WIDTH, NUMBER_ICON_HEIGHT));
        repaint();
    }
}