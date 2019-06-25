package ru.turlyunef.focusstart.turlyun;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Stock with capacity STOCK_CAPACITY.
 * Allows you to add resources to a thread-safe queue and take them from it.
 */
class Stock {
    private static final int STOCK_CAPACITY = 10;
    private static final Queue<Resource> resources = new ArrayBlockingQueue<>(STOCK_CAPACITY);

    /**
     * Adds resources to a thread-safe queue.
     *
     * @param resource resource that is added to the queue
     * @return true, if adding happened successfully, otherwise false
     */
    boolean putResource(Resource resource) {
        if (resources.size() < STOCK_CAPACITY) {
            resources.add(resource);

            return true;
        }

        return false;

    }

    /**
     * Gets a resource from the stock.
     *
     * @return resource
     */
    Resource getResource() {

        return resources.poll();
    }
}