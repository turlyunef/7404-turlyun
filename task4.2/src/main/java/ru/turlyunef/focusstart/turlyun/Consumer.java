package ru.turlyunef.focusstart.turlyun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Task for threads that consume resources.
 * Id registration occurs in the Recorder class.
 * Each consumer takes from the Stock 1 unit of resource in CONSUME_TIME milliseconds.
 */
class Consumer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);
    private static final int CONSUME_TIME = 1000;
    private final int id;

    /**
     * Constructor registries consumer id.
     */
    Consumer() {
        this.id = Recorder.getConsumerId();
    }

    /**
     * Launches the consumer.
     */
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            getResource();
        }
    }

    /**
     * Consumes resource from stock.
     */
    private void getResource() {
        Resource resource = Stock.getResource();
        if (resource != null) {
            logger.info(String.format("Consumer with id = %d successfully get from the stock resource with id %d.", this.id, resource.getId()));
            consumeResource(resource);
        }
    }

    /**
     * Consumes resource.
     */
    private void consumeResource(Resource resource) {
        try {
            Thread.sleep(CONSUME_TIME);
            logger.info(String.format("Resource with id = %d was successfully consumed by the consumer with id = %d", resource.getId(), this.id));
        } catch (InterruptedException e) {
            logger.info(String.format("Work of consumer with id = %d was interrupted cause %s", this.id, e.getCause()));
            Thread.currentThread().interrupt();
        }
    }
}