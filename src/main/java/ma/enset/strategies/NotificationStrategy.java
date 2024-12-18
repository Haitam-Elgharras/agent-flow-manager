package ma.enset.strategies;

import ma.enset.entities.Agent;
import ma.enset.entities.Transaction;

public interface NotificationStrategy {
    void processNotification(Agent notifier, Transaction transaction);
}