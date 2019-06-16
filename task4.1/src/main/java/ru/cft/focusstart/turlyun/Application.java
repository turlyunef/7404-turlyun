package ru.cft.focusstart.turlyun;

/**
 * The application is designed to calculate the value of functions in one stream and streams,
 * the number of which is equal to the number of computer cores.
 *
 * @author Turlyun Evgeny Fedorovich
 * @see ThreadManager
 */
public class Application {
    public static void main(String[] args) {
        ThreadManager.count();
    }
}