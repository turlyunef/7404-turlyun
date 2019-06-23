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
        while (true) {
            produceResource();
        }
    }

    /**
     * Produces resource and puts it to the stock.
     */
    private void produceResource() {
        Resource resource = new Resource();
        logger.info(String.format("Resource with id = %s was successfully created.", resource.getId()));
        if (Stock.putResource(resource)) {
            logger.info(String.format("Producer with id = %s successfully put in the stock resource with id = %d.", this.id, resource.getId()));
        } else {
            logger.info(String.format("Producer with id = %s don't put in the stock resource with id %d, wait...", this.id, resource.getId()));
        }
        try {
            Thread.sleep(PRODUCE_TIME);
        } catch (InterruptedException e) {
            logger.info(String.format("Producer with id = %d was interrupted cause %s", this.id, e.getCause()));
        }
    }
}