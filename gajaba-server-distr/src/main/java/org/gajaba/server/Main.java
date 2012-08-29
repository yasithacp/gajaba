package org.gajaba.server;

import org.gajaba.group.GMSSeparator;
import org.gajaba.nio2.Proxy;
import org.gajaba.rule.core.RuleDefinition;
import org.gajaba.simulator.Simulator;

public class Main {

    public static void main(String[] args) {

        RuleDefinition def = RuleDefinition.createRuleDefinition("@ip=serverIp;");

        Server server = new Server(def);
        server.start();

        Simulator simulator = new Simulator();
        try {
            simulator.startServer(server, new GMSSeparator());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Proxy proxy = new Proxy(def);
            proxy.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
