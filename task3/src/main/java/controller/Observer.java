package controller;

public interface Observer {
    void handleEvent();

    void handleEvent(int number, String observerName);
}