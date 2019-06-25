package ru.turlyunef.focusstart.turlyun;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Application implements the production scheme - resource consumption.
 * Registration of resources, producers and consumers occurs automatically in the static Recorder class.
 * Resources are stored in a static Stock class.
 *
 * @author Turlyun Evgeny Fedorovich
 */
public class Application {
    private static final int PRODUCERS_COUNT = 3;
    private static final int CONSUMERS_COUNT = 1;
    private static Stock stock = new Stock();
    private static Object lock = new Object();

    /**
     * Launches production and resource consumption.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        createProducers();
        createConsumers();
    }

    /**
     * Launches resource consumption.
     */
    private static void createConsumers() {
        ExecutorService consumers = Executors.newFixedThreadPool(CONSUMERS_COUNT);
        for (int i = 0; i < CONSUMERS_COUNT; i++) {
            consumers.submit(new Consumer(stock, lock));
        }
    }

    /**
     * Launches production consumption.
     */
    private static void createProducers() {
        ExecutorService producers = Executors.newFixedThreadPool(PRODUCERS_COUNT);
        for (int i = 0; i < PRODUCERS_COUNT; i++) {
            producers.submit(new Producer(stock, lock));
        }
    }
}