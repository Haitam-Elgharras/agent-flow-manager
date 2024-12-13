package entities;

import aspects.annotations.Cachable;
import aspects.annotations.Log;
import aspects.annotations.SecuredBy;
import strategies.DefaultStrategy;
import strategies.NotificationStrategy;
import observers.Observable;
import observers.Observer;

import java.util.ArrayList;
import java.util.List;

public class Agent implements Observer, Observable {
    private final String name;
    private final List<Transaction> transactions;
    private final List<Observer> observers;
    private NotificationStrategy notificationStrategy;

    public Agent(String name) {
        this.name = name;
        this.transactions = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.notificationStrategy = new DefaultStrategy();
    }

    @Override
    public void subscribe(Observer observer) {
        System.out.println("Agent " + name + " subscribed to " + ((Agent) observer).name);
        observers.add(observer);
    }

    @Override
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    @Override
    @Log
    public void notifyObservers(Transaction transaction) {
        System.out.println("Agent " + name + " notifying observers about transaction: " + transaction.getId());
        for (Observer observer : observers) {
            observer.update(this, transaction);
        }
    }

    @SecuredBy(roles = {"ADMIN"})
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        notifyObservers(transaction);
    }

    // Observer method
    @Override
    public void update(Agent notifier, Transaction transaction) {
        notificationStrategy.processNotification(notifier, transaction);
    }

    public void setNotificationStrategy(NotificationStrategy strategy) {
        this.notificationStrategy = strategy;
    }

    @Cachable
    public Transaction getHighestTransaction() {
        return transactions.stream().max((t1, t2) -> Double.compare(t1.getAmount(), t2.getAmount())).orElse(null);
    }

    public String getName() {
        return name;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
