package ru.turlyunef.focusstart.turlyun;

/**
 * Resource class.
 * Used by producers and consumers.
 * Id registration occurs in the Recorder class.
 */
class Resource {
    private final int id;

    /**
     * Constructor registries resource id.
     */
    Resource() {
        this.id = Recorder.getResourceId();
    }

    /**
     * Gets resource id.
     *
     * @return resource id
     */
    int getId() {

        return id;
    }
}