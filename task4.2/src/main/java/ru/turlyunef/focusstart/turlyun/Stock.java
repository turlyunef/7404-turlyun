package ru.turlyunef.focusstart.turlyun;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

class Stock {
    private static final int STOCK_CAPACITY = 10;
    private static final Queue<Resource> resources = new ArrayBlockingQueue<>(STOCK_CAPACITY);

    static boolean putResource(Resource resource) {
        if (resources.size() < STOCK_CAPACITY) {
            resources.add(resource);

            return true;
        }

        return false;
    }

    static Resource getResource() {

        return resources.poll();
    }
}