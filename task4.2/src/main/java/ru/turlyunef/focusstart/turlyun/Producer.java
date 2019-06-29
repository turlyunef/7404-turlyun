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
        while (!Thread.interrupted()) {
            putResourceToStock();
        }
    }

    /**
     * Puts resource to the stock.
     */
    private void putResourceToStock() {
        try {
            Resource resource = createResource();
            Stock.putResource(resource);
            logger.info(String.format("Producer with id = %s successfully put in the stock resource with id = %d.", this.id, resource.getId()));
        } catch (InterruptedException e) {
            logger.info(String.format("Work of producer with id = %d was interrupted cause %s", this.id, e.getCause()));
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Creates resource.
     */
    private Resource createResource() throws InterruptedException {
        Thread.sleep(PRODUCE_TIME);
        Resource resource = new Resource();
        logger.info(String.format("Resource with id = %s was successfully created by the producer with id = %d.", resource.getId(), this.id));

        return resource;
    }
}