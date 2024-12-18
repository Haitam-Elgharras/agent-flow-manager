package ma.enset.entities;

import ma.enset.display.HDMI;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Container {
    private static Container instance;
    private final Map<String, Agent> agents;
    private HDMI hdmi;

    private Container() {
        agents = new HashMap<>();
    }

    public static synchronized Container getInstance() {
        if (instance == null) {
            instance = new Container();
        }
        return instance;
    }

    public void addAgent(Agent agent) {
        agents.put(agent.getName(), agent);
    }

    public void removeAgent(String name) {
        agents.remove(name);
    }

    public Agent findAgent(String name) {
        return agents.get(name);
    }

    public void setHDMI(HDMI hdmi) {
        this.hdmi = hdmi;
    }

    public void display() {
        agents.values().forEach(agent -> {
            hdmi.display(agent.getName().toCharArray());
        });
    }
}