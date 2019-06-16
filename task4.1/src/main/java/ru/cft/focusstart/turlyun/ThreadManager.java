package ru.cft.focusstart.turlyun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for managing threads in calculating.
 */
class ThreadManager {
    private static final int CALCULATIONS_COUNT = 500_000;
    private static final int PROCESSORS_COUNT = Runtime.getRuntime().availableProcessors();
    private static final Logger log = LoggerFactory.getLogger(ThreadManager.class);
    private static Task[] tasks;
    private static Thread[] threads;
    private static long timerStarted;

    /**
     * Counts the result in one and several threads.
     */
    static void count() {
        countWithOneTask();
        countWithManyTasks();
    }

    /**
     * Counts the result in one thread.
     */
    private static void countWithOneTask() {
        log.info("Counting with one task is started.");
        timerStarted = System.currentTimeMillis();
        Task task = new Task(1, CALCULATIONS_COUNT, Function::function);
        Thread thread = new Thread(task);

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info(String.format("Counting with one task is over. Lead time is %d ns.%n",
                System.currentTimeMillis() - timerStarted));
    }

    /**
     * Counts the result in several threads.
     */
    private static void countWithManyTasks() {
        createTasks();
        createThreads();
        startThreads();
    }

    /**
     * Creates a function calculation tasks with a part of the arguments for one from the total number of them.
     */
    private static void createTasks() {
        timerStarted = System.currentTimeMillis();
        tasks = new Task[PROCESSORS_COUNT];
        for (int i = 0; i < PROCESSORS_COUNT; i++) {
            int minArgument = i * CALCULATIONS_COUNT / PROCESSORS_COUNT + 1;
            int maxArgument = (i + 1) * CALCULATIONS_COUNT / PROCESSORS_COUNT;
            tasks[i] = new Task(minArgument, maxArgument, Function::function);
            log.debug(String.format("Task %d created with next parameters: minArgument = %d, maxArgument = %d.", i + 1,
                    minArgument, maxArgument));
        }
    }

    /**
     * Creates a function calculation threads.
     */
    private static void createThreads() {
        threads = new Thread[PROCESSORS_COUNT];
        for (int i = 0; i < PROCESSORS_COUNT; i++) {
            threads[i] = new Thread(tasks[i]);
            log.debug(String.format("Thread %d created based task %d.", i + 1, i + 1));
        }
    }

    /**
     * Starts a function calculation threads.
     */
    private static void startThreads() {
        for (Thread thread : threads) {
            log.debug(thread.getName() + " started.");
            thread.start();
        }
        joinThreads();
        sumResults();
        log.info(String.format("Counting with many task is over. Lead time is %d ns.",
                System.currentTimeMillis() - timerStarted));
    }

    /**
     * Joins the created threads to the main thread.
     */
    private static void joinThreads() {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Summarizes the result of calculations from multiple streams to a single value.
     */
    private static void sumResults() {
        long result = 0;
        for (Task task : tasks) {
            result += task.getResult();
        }
        log.info(String.format("Result is %d.", result));
    }
}