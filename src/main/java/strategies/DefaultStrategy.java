package strategies;

import entities.Agent;
import entities.Transaction;

public class DefaultStrategy implements NotificationStrategy {
    @Override
    public void processNotification(Agent notifier, Transaction transaction) {
        System.out.println("Default notification processing for transaction: " + transaction + " sent by " + notifier.getName());
    }
}