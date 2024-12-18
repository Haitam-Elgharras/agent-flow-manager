package ma.enset;

import ma.enset.aspects.SecurityContext;
import ma.enset.display.HDMI;
import ma.enset.display.HDMIAdapter;
import ma.enset.display.VGA;
import ma.enset.entities.Agent;
import ma.enset.entities.Container;
import ma.enset.entities.Transaction;
import ma.enset.entities.TransactionType;
import ma.enset.service.IAgentManagementService;
import ma.enset.strategies.HistoryStrategy;
import ma.enset.strategies.ScoringStrategy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;
import java.util.Date;

@ComponentScan(basePackages = {"ma.enset"})
public class Application {
    public static void main(String[] args) {

        ApplicationContext context =
                new AnnotationConfigApplicationContext(Application.class);

        // create agent management service
        IAgentManagementService agentManagementService = context.getBean(IAgentManagementService.class);

        // authenticate
        SecurityContext.authenticate("admin", "1234", new String[]{"ADMIN", "USER"});

        // create agents
        agentManagementService.addAgent("Agent1");
        agentManagementService.addAgent("Agent2");
        agentManagementService.addAgent("Agent3");
        agentManagementService.addAgent("Agent4");

        // agent 2 subscribes to agent 1

        // first set the strategy
        agentManagementService.setNotificationStrategy("Agent2", "history");
        agentManagementService.setNotificationStrategy("Agent3", "history");
        agentManagementService.setNotificationStrategy("Agent4", "history");

        // then subscribe
        agentManagementService.subscribe("Agent1", "Agent2");
        agentManagementService.subscribe("Agent1", "Agent3");
        agentManagementService.subscribe("Agent1", "Agent4");

        // create transactions
        Transaction transaction1 = new Transaction.Builder()
                .id("1")
                .date(new Date())
                .amount(100)
                .type(TransactionType.ACHAT)
                .build();

        agentManagementService.addTransaction("Agent1", transaction1);
    }
}