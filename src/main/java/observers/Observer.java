package observers;

import entities.Agent;
import entities.Transaction;

public interface Observer {
    void update(Agent notifier, Transaction transaction);
}