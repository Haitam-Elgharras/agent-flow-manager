package observers;

import entities.Transaction;

public interface Observable {
    void subscribe(Observer observer);
    void unsubscribe(Observer observer);
    void notifyObservers(Transaction transaction);
}