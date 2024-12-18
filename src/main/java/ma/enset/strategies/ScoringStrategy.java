package ma.enset.strategies;

import ma.enset.entities.Agent;
import ma.enset.entities.Transaction;
import ma.enset.entities.TransactionType;
import org.springframework.stereotype.Component;

@Component
public class ScoringStrategy implements NotificationStrategy {
    private double score = 0;
    @Override
    public void processNotification(Agent notifier, Transaction transaction) {
        score = transaction.getType() == TransactionType.VENTE ? score + transaction.getAmount() : score - transaction.getAmount();
        System.out.println("Updated score for agent " + notifier.getName() + ": " + score);
    }
}