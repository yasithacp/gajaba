package org.gajaba.server;

import org.gajaba.group.GMSSeparator;
import org.gajaba.nio2.Proxy;
import org.gajaba.rule.core.RuleDefinition;
import org.gajaba.simulator.Simulator;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class Main {

    @Option(name="-r",usage="rules to be executed")
    private String rule = ";";

    public static void main(String[] args) {
        new Main().runMain(args);
    }

    private void runMain(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            System.out.println(rule);
        } catch (CmdLineException e) {
            e.printStackTrace();
        }

        GMSSeparator separator = new GMSSeparator();
        RuleDefinition def = RuleDefinition.createRuleDefinition(rule, separator);

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
