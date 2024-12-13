package strategies;

import entities.Agent;
import entities.Transaction;

public interface NotificationStrategy {
    void processNotification(Agent notifier, Transaction transaction);
}