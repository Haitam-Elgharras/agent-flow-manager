package strategies;

import entities.Transaction;
import entities.Agent;

import java.util.ArrayList;
import java.util.List;

public class HistoryStrategy implements NotificationStrategy {
    private List<Transaction> history = new ArrayList<Transaction>();
    @Override
    public void processNotification(Agent notifier, Transaction transaction) {
        history.add(transaction);
        this.printHistory();
    }

    public void printHistory() {
        for (Transaction transaction : history) {
            System.out.println(transaction);
        }
    }
}