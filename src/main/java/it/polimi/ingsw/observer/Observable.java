package it.polimi.ingsw.observer;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {

    private transient List<Observer<T>> observers = new ArrayList<>();

    public void addObserver(Observer<T> observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void setObserver(Observer<T> observer) {
        synchronized (observers) {
            observers = new ArrayList<>();
            observers.add(observer);
        }
    }

    public  List<Observer<T>> getObservers() {
        return observers;
    }

    public void addObserver(List<Observer<T>> observers) {
        synchronized (observers) {
            this.observers.addAll(observers);
        }
    }

    public void removeObserver(Observer<T> observer) {
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    protected void notify(T message) {
        synchronized (observers) {
            for (Observer<T> observer : observers) {
                observer.update(message);
            }
        }
    }

}
