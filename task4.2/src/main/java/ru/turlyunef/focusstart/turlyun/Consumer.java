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
        while (true) {
            consumeResource();
        }
    }

    /**
     * Consumes resource from stock.
     */
    private void consumeResource() {
        Resource resource = Stock.getResource();
        if (resource != null) {
            logger.info(String.format("Consumer with id = %d successfully get from the stock resource with id %d.", this.id, resource.getId()));
            logger.info(String.format("Resource with id = %d was successfully consumed.", resource.getId()));
        } else {
            logger.info(String.format("Consumer with id = %d don't get from the stock resource, wait...", this.id));
        }
        try {
            Thread.sleep(CONSUME_TIME);
        } catch (InterruptedException e) {
            logger.info(String.format("Consumer with id = %d was interrupted cause %s", this.id, e.getCause()));
        }
    }
}