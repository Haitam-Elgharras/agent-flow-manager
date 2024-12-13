import aspects.SecurityContext;
import display.HDMI;
import display.HDMIAdapter;
import display.VGA;
import entities.Agent;
import entities.Container;
import entities.Transaction;
import entities.TransactionType;
import org.springframework.context.annotation.ComponentScan;
import strategies.HistoryStrategy;
import strategies.ScoringStrategy;

import java.util.Arrays;
import java.util.Date;

@ComponentScan(basePackages = {"entities", "display", "strategies", "aspects", "observers"})
public class Application {
    public static void main(String[] args) {
        // Authenticate
        SecurityContext.authenticate("admin", "1234", new String[]{"ADMIN", "USER"});

        // Create agents
        Agent agent1 = new Agent("Agent1");
        Agent agent2 = new Agent("Agent2");

        // Subscribe agents
        agent1.subscribe(agent2);

        // Create transactions
        Transaction transaction1 = new Transaction.Builder()
                .id("T1")
                .date(new Date())
                .amount(100)
                .type(TransactionType.VENTE)
                .build();

        Transaction transaction2 = new Transaction.Builder()
                .id("T2")
                .date(new Date())
                .amount(200)
                .type(TransactionType.ACHAT)
                .build();

        // Add transactions
        agent1.addTransaction(transaction1);
        agent1.addTransaction(transaction2);

        // Change strategy
        agent2.setNotificationStrategy(new ScoringStrategy());
        agent1.addTransaction(transaction1);

        // Use HistoryStrategy
        agent2.setNotificationStrategy(new HistoryStrategy());
        agent1.addTransaction(transaction2);

        // Display container state
        Container container = Container.getInstance();
        container.addAgent(agent1);
        container.addAgent(agent2);

        // HDMI display
        HDMI hdmiDisplay = data -> System.out.println("HDMI Display: " + Arrays.toString(data));
        container.display();

        // VGA display
        VGA vgaDisplay = data -> System.out.println("VGA Display: " + data);
        HDMIAdapter hdmiAdapter = new HDMIAdapter(vgaDisplay);
        hdmiAdapter.display("VGA data".toCharArray());
    }
}