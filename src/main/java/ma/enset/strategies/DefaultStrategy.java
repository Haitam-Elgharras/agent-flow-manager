package ma.enset.strategies;

import ma.enset.entities.Agent;
import ma.enset.entities.Transaction;
import org.springframework.stereotype.Component;

@Component
public class DefaultStrategy implements NotificationStrategy {
    @Override
    public void processNotification(Agent notifier, Transaction transaction) {
        System.out.println("Default notification processing for transaction: " + transaction + " sent by " + notifier.getName());
    }
}