package ru.turlyunef.focusstart.turlyun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Stock with capacity STOCK_CAPACITY.
 * Allows you to add resources to a thread-safe queue and take them from it.
 */
class Stock {
    private static final Logger logger = LoggerFactory.getLogger(Stock.class);
    private static final int STOCK_CAPACITY = 10;
    private static final ArrayBlockingQueue<Resource> resources = new ArrayBlockingQueue<>(STOCK_CAPACITY);

    /**
     * Adds resources to a thread-safe queue.
     *
     * @param resource resource that is putted to the queue
     * @return true, if adding happened successfully, otherwise false
     */
    static boolean putResource(Resource resource) {
            if (resources.size() < STOCK_CAPACITY) {
                try {
                    resources.put(resource);
                } catch (InterruptedException e) {
                    logger.error("Failed to put resource in the stock.");
                    Thread.currentThread().interrupt();
                }

                return true;
            }

            return false;
    }

    /**
     * Gets a resource from the stock.
     *
     * @return resource
     */
    static Resource getResource() {
            Resource resource = null;
            try {
                 resource = resources.take();
            } catch (InterruptedException e) {
                logger.error("Failed to take resource from the stock.");
                Thread.currentThread().interrupt();
            }

            return resource;
    }
}