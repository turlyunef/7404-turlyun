package controller;

public interface Observed {
    void addObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObservers();

    void notifyObservers(int number, String observerName);
}