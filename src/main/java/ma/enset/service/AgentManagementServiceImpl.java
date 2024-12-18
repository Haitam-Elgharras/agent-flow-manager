package ma.enset.service;

import ma.enset.aspects.SecurityContext;
import ma.enset.aspects.annotations.Log;
import ma.enset.aspects.annotations.SecuredBy;
import ma.enset.entities.Agent;
import ma.enset.entities.Transaction;
import ma.enset.strategies.DefaultStrategy;
import ma.enset.strategies.HistoryStrategy;
import ma.enset.strategies.ScoringStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AgentManagementServiceImpl implements IAgentManagementService {
    private final Map<String, Agent> agents;

    @Autowired
    public AgentManagementServiceImpl(SecurityContext securityContext) {
        this.agents = new HashMap<>();
    }

    @Override
    @Log
    @SecuredBy(roles = {"ADMIN"})
    public void addAgent(String agentName) {
        agents.put(agentName, new Agent(agentName));
    }

    @Override
    public void deleteAgent(String agentName) {
        agents.remove(agentName);
    }

    @Override
    public void updateAgent(String agentName) {
        // Implement update logic if needed
    }

    @Override
    public Agent getAgent(String agentName) {
        return agents.get(agentName);
    }

    @Override
    @Log
    @SecuredBy(roles = {"ADMIN"})
    public void addTransaction(String agentName, Transaction transaction) {
        Agent agent = agents.get(agentName);
        if (agent != null) {
            agent.addTransaction(transaction);
        }
    }

    @Override
    public void deleteTransaction(String agentName, Transaction transaction) {
        Agent agent = agents.get(agentName);
        if (agent != null) {
            agent.getTransactions().remove(transaction);
        }
    }

    @Override
    public void updateTransaction(String agentName, Transaction transaction) {
//        Agent agent = agents.get(agentName);
//        if (agent != null) {
//            agent.getTransactions().stream()
//                    .filter(t -> t.equals(transaction))
//                    .findFirst()
//                    .ifPresent(t -> t.setAmount(transaction.getAmount()));
//        }
    }

    @Override
    public Transaction getTransaction(String agentName, Transaction transaction) {
        Agent agent = agents.get(agentName);
        if (agent != null) {
            agent.getTransactions().stream()
                    .filter(t -> t.equals(transaction))
                    .findFirst()
                    .orElse(null);
        }
        return transaction;
    }

    @Override
    public void subscribe(String agentName, String observerName) {
        Agent agent = agents.get(agentName);
        Agent observer = agents.get(observerName);
        if (agent != null && observer != null) {
            agent.subscribe(observer);
        }
    }

    @Override
    public void unsubscribe(String agentName, String observerName) {
        Agent agent = agents.get(agentName);
        Agent observer = agents.get(observerName);
        if (agent != null && observer != null) {
            agent.unsubscribe(observer);
        }
    }

    @Override
    public void notifyObservers(String agentName, Transaction transaction) {
        Agent agent = agents.get(agentName);
        if (agent != null) {
            agent.notifyObservers(transaction);
        }
    }

    @Override
    @Log
    public void setNotificationStrategy(String agentName, String strategyName) {
        Agent agent = agents.get(agentName);
        if (agent != null) {
            switch (strategyName.toLowerCase()) {
                case "scoring":
                    agent.setNotificationStrategy(new ScoringStrategy());
                    break;
                case "history":
                    agent.setNotificationStrategy(new HistoryStrategy());
                    break;
                default:
                    agent.setNotificationStrategy(new DefaultStrategy());
                    break;
            }
        }
    }

    @Override
    public void authenticate(String username, String password, String[] roles) {
        SecurityContext.authenticate(username, password, roles);
    }

    @Override
    public boolean isAuthenticated() {
        return SecurityContext.isAuthenticated();
    }

    @Override
    public boolean hasRole(String role) {
        return SecurityContext.hasRole(role);
    }
}