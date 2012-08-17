package org.gajaba.server;


import org.gajaba.nio2.Proxy;
import org.gajaba.rule.core.Client;
import org.gajaba.rule.core.RuleDefinition;

import javax.script.Bindings;
import javax.script.SimpleBindings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        RuleDefinition def = RuleDefinition.createRuleDefinition("@ip=serverIp;");

        try {
            Proxy proxy = new Proxy(def);
            proxy.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Server server = new Server(def);
        server.start();


    }
}
