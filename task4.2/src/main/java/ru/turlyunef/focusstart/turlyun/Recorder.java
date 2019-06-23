package ru.turlyunef.focusstart.turlyun;

import java.util.concurrent.atomic.AtomicInteger;

class Recorder {
    private static final AtomicInteger resourceIdCounter = new AtomicInteger(1);
    private static final AtomicInteger producerIdCounter = new AtomicInteger(1);
    private static final AtomicInteger consumerIdCounter = new AtomicInteger(1);

    static int getResourceId() {

        return resourceIdCounter.getAndIncrement();
    }

    static int getProducerId() {

        return producerIdCounter.getAndIncrement();
    }

    static int getConsumerId() {

        return consumerIdCounter.getAndIncrement();
    }
}