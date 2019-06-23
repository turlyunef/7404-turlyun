package ru.turlyunef.focusstart.turlyun;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {
    private static final int PRODUCERS_COUNT = 2;
    private static final int CONSUMERS_COUNT = 1;

    public static void main(String[] args) {
        createProducers();
        createConsumers();
    }

    private static void createConsumers() {
        ExecutorService consumers = Executors.newFixedThreadPool(CONSUMERS_COUNT);
        for (int i = 0; i < CONSUMERS_COUNT; i++) {
            consumers.submit(new Consumer());
        }
    }

    private static void createProducers() {
        ExecutorService producers = Executors.newFixedThreadPool(PRODUCERS_COUNT);
        for (int i = 0; i < PRODUCERS_COUNT; i++) {
            producers.submit(new Producer());
        }
    }
}