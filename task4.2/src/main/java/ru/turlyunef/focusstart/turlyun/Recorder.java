package ru.turlyunef.focusstart.turlyun;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class registration of resources, producers and consumers.
 */
class Recorder {
    private static final AtomicInteger resourceIdCounter = new AtomicInteger(1);
    private static final AtomicInteger producerIdCounter = new AtomicInteger(1);
    private static final AtomicInteger consumerIdCounter = new AtomicInteger(1);

    /**
     * Gets and counts id for a resources.
     *
     * @return resource id
     */
    static int getResourceId() {

        return resourceIdCounter.getAndIncrement();
    }

    /**
     * Gets and counts id for a producers.
     *
     * @return producers id
     */
    static int getProducerId() {

        return producerIdCounter.getAndIncrement();
    }

    /**
     * Gets and counts id for a consumers.
     *
     * @return consumers id
     */
    static int getConsumerId() {

        return consumerIdCounter.getAndIncrement();
    }
}