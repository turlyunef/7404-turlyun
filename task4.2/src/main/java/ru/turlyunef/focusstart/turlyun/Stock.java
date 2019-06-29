package ru.turlyunef.focusstart.turlyun;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Stock with capacity STOCK_CAPACITY.
 * Adds resources to a thread-safe queue and take them from it.
 */
class Stock {
    private static final int STOCK_CAPACITY = 10;
    private static final ArrayBlockingQueue<Resource> resources = new ArrayBlockingQueue<>(STOCK_CAPACITY);

    /**
     * Adds resources to a thread-safe queue.
     *
     * @param resource resource that is putted to the queue
     */
    static void putResource(Resource resource) throws InterruptedException {
        resources.put(resource);
    }

    /**
     * Gets a resource from the stock.
     *
     * @return resource
     */
    static Resource getResource() throws InterruptedException {

        return resources.take();
    }
}