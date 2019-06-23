package ru.turlyunef.focusstart.turlyun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Consumer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);
    private static final int CONSUME_TIME = 1000;
    private final int id;

    Consumer() {
        this.id = Recorder.getConsumerId();
    }

    @Override
    public void run() {
        while (true) {
            consumeResource();
        }
    }

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
            e.printStackTrace();
        }
    }
}