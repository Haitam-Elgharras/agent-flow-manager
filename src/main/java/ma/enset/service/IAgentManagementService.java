package ma.enset.service;

import ma.enset.entities.Agent;
import ma.enset.entities.Transaction;

public interface IAgentManagementService {
    public void addAgent(String agentName);
    public void deleteAgent(String agentName);
    public void updateAgent(String agentName);
    public Agent getAgent(String agentName);

    // transaction
    public void addTransaction(String agentName, Transaction transaction);
    public void deleteTransaction(String agentName, Transaction transaction);
    public void updateTransaction(String agentName, Transaction transaction);
    public Transaction getTransaction(String agentName, Transaction transaction);

    // observer
    public void subscribe(String agentName, String observerName);
    public void unsubscribe(String agentName, String observerName);
    public void notifyObservers(String agentName, Transaction transaction);
    public void setNotificationStrategy(String agentName, String strategyName);

    // security
    public void authenticate(String username, String password, String[] roles);
    public boolean isAuthenticated();
    public boolean hasRole(String role);
}
