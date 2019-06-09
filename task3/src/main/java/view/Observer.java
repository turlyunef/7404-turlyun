package view;

import controller.event.Event;

public interface Observer {
    /**
     * Performs the action as directed by the observed object.
     *
     * @param event event of any changes
     */
    void handleEvent(Event event);
}