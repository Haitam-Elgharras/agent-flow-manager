package strategies;

import entities.Agent;
import entities.Transaction;
import entities.TransactionType;

public class ScoringStrategy implements NotificationStrategy {
    private double score = 0;
    @Override
    public void processNotification(Agent notifier, Transaction transaction) {
        score = transaction.getType() == TransactionType.VENTE ? score + transaction.getAmount() : score - transaction.getAmount();
        System.out.println("Updated score for agent " + notifier.getName() + ": " + score);
    }
}