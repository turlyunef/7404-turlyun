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
    private Stock stock;
    private final Object lock;

    /**
     * Constructor registries producer id.
     */
    Producer(Stock stock, Object lock) {
        this.id = Recorder.getProducerId();
        this.stock = stock;
        this.lock = lock;
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
        synchronized (lock) {
            logger.info(String.format("!Resource with id = %s was successfully created.", resource.getId()));
            if (stock.putResource(resource)) {
                logger.info(String.format("!Producer with id = %s successfully put in the stock resource with id = %d.", this.id, resource.getId()));
                lock.notifyAll();
                createResource();
            } else {
                logger.info(String.format("!Producer with id = %s don't put in the stock resource with id %d, wait...", this.id, resource.getId()));
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
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
            logger.info(String.format("!Producer with id = %d was interrupted cause %s", this.id, e.getCause()));
            Thread.currentThread().interrupt();
        }
    }
}