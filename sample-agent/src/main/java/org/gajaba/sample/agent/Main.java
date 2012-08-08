package org.gajaba.sample.agent;


import org.gajaba.agent.Agent;

public class Main {
    public static void main(String[] args) {
        Agent agent = new Agent();
        agent.start(args[0]);
    }
}
