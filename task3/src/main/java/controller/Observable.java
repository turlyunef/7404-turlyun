package controller;

import controller.event.Event;

public interface Observable {

    /**
     * Adds the observer object to the observers list for this object.
     *
     * @param o observer object
     */
    void addObserver(Observer o);

    /**
     * Removes the observer object from the observers list for this object.
     *
     * @param o observer object
     */
    void removeObserver(Observer o);

    /**
     * Notify all observer objects from the observers list for this object.
     *
     * @param event event of any changes
     */
    void notifyObservers(Event event);
}