package ru.turlyunef.focusstart.turlyun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Task for threads that produce resources.
 * Id registration occurs in the Recorder class.
 * Each producer put to the Stock 1 unit of resource in PRODUCE_TIME milliseconds.
 */
class Producer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private static final int PRODUCE_TIME = 1000;
    private final int id;
    private Resource resource;

    /**
     * Constructor registries producer id.
     */
    Producer() {
        this.id = Recorder.getProducerId();
    }

    /**
     * Launches the producer.
     */
    @Override
    public void run() {
        createResource();
        while (!Thread.interrupted()) {
            putResourceToStock();
        }
    }

    /**
     * Puts resource to the stock.
     */
    private void putResourceToStock() {
        logger.info(String.format("Resource with id = %s was successfully created by the producer with id = %d.", resource.getId(), this.id));
        if (Stock.putResource(resource)) {
            logger.info(String.format("Producer with id = %s successfully put in the stock resource with id = %d.", this.id, resource.getId()));
            createResource();
        }
    }

    /**
     * Creates resource.
     */
    private void createResource() {
        try {
            Thread.sleep(PRODUCE_TIME);
            this.resource = new Resource();
        } catch (InterruptedException e) {
            logger.info(String.format("Work of producer with id = %d was interrupted cause %s", this.id, e.getCause()));
            Thread.currentThread().interrupt();
        }
    }
}