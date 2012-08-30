package org.gajaba.server;

import org.gajaba.group.GMSSeparator;
import org.gajaba.nio2.Proxy;
import org.gajaba.rule.core.RuleDefinition;
import org.gajaba.simulator.Simulator;

public class Main {

    public static void main(String[] args) {

        GMSSeparator separator = new GMSSeparator();
        RuleDefinition def = RuleDefinition.createRuleDefinition("#@url['/user/(\\\\w*)/.*',1]='true';",separator);

        Server server = new Server(def);
        server.start();

        startProxy(def);

        Simulator simulator = new Simulator();
        try {
            simulator.startServer(server, separator);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static void startProxy(final RuleDefinition def) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Proxy proxy = new Proxy(def);
                    proxy.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(runnable).start();
    }
}
