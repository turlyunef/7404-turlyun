package controller;

public interface Observer {
    /**
     * Performs the action as directed by the observed object.
     */
    void handleEvent();

    /**
     * Performs the action with the transmitted number as directed by the observed object,
     * tagged with which observer the number is intended.
     *
     * @param number       transmitted number for observer
     * @param observerName tag with which observer the number is intended
     */
    void handleEvent(int number, String observerName);
}