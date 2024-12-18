package ma.enset.observers;

import ma.enset.entities.Agent;
import ma.enset.entities.Transaction;

public interface Observer {
    void update(Agent notifier, Transaction transaction);
}