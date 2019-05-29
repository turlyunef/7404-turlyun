package controller;

public interface Observed {

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
     */
    void notifyObservers();

    /**
     * Pass the number to all observer objects from the observers list for this object,
     * tagged with which observer the number is intended.
     *
     * @param number       transmitted number for observer
     * @param observerName tag with which observer the number is intended
     */
    void notifyObservers(int number, String observerName);
}