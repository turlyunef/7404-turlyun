package ru.cft.focusstart.turlyun;

class ThreadManager {
    private static final int CALCULATIONS_COUNT = 1_000_000;
    private static final int PROCESSORS_COUNT = Runtime.getRuntime().availableProcessors();
    private static Task[] tasks;
    private static Thread[] threads;
    private static long timerStarted;

    static void count() {
        countWithOneTask();
        countWithManyTasks();
    }

    private static void countWithOneTask() {
        System.out.println("Counting with one task is started.");
        timerStarted = System.currentTimeMillis();
        Task task = new Task(1, CALCULATIONS_COUNT, Functions::function);
        Thread thread = new Thread(task);

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%nCounting with one task is over. Lead time is %d ns%n%n",
                System.currentTimeMillis() - timerStarted);
    }

    private static void countWithManyTasks() {
        createTasks();
        createThreads();
        startThreads();
    }

    private static void createTasks() {
        timerStarted = System.currentTimeMillis();
        tasks = new Task[PROCESSORS_COUNT];
        for (int i = 0; i < PROCESSORS_COUNT; i++) {
            int minArgument = i * CALCULATIONS_COUNT / PROCESSORS_COUNT + 1;
            int maxArgument = (i + 1) * CALCULATIONS_COUNT / PROCESSORS_COUNT;
            tasks[i] = new Task(minArgument, maxArgument, Functions::function);
            System.out.printf("Task %d created with next perameters: minArgument = %d, maxArgument = %d.%n", i + 1,
                    minArgument, maxArgument);
        }
        System.out.println();
    }

    private static void createThreads() {
        threads = new Thread[PROCESSORS_COUNT];
        for (int i = 0; i < PROCESSORS_COUNT; i++) {
            threads[i] = new Thread(tasks[i]);
            System.out.printf("Thread %d created based task %d.%n", i + 1, i + 1);
        }
        System.out.println();
    }

    private static void startThreads() {
        for (Thread thread : threads) {
            System.out.println(thread.getName() + " started.");
            thread.start();
        }
        joinThreads();
        sumResults();
        System.out.printf("%nCounting with many task is over. Lead time is %d ns",
                System.currentTimeMillis() - timerStarted);
    }

    private static void joinThreads() {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void sumResults() {
        long result = 0;
        for (Task task : tasks) {
            result += task.getResult();
        }
        System.out.printf("%nResult is %d %n", result);
    }
}