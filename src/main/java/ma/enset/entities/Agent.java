package ma.enset.entities;

import ma.enset.aspects.annotations.Cacheable;
import ma.enset.aspects.annotations.Log;
import ma.enset.aspects.annotations.SecuredBy;
import ma.enset.strategies.DefaultStrategy;
import ma.enset.strategies.NotificationStrategy;
import ma.enset.observers.Observable;
import ma.enset.observers.Observer;

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
        observers.add(observer);
    }

    @Override
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Transaction transaction) {
        for (Observer observer : observers) {
            observer.update(this, transaction);
        }
    }

    @SecuredBy(roles = {"ADMIN"})
    @Log
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

    public NotificationStrategy getNotificationStrategy() {
        return notificationStrategy;
    }

    @Cacheable
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
