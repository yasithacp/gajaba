package org.gajaba.sample.agent;


import org.gajaba.agent.Agent;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Agent agent = new Agent();
        if (args.length > 0) {
            agent.start(args[0]);
        } else {
            agent.start();
        }
        Thread.sleep(5000);
        agent.publish("user","a");
    }
}
