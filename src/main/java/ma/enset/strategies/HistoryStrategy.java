package ma.enset.strategies;

import ma.enset.entities.Transaction;
import ma.enset.entities.Agent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HistoryStrategy implements NotificationStrategy {
    private List<Transaction> history = new ArrayList<>();
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