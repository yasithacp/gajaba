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
        try {
            Bindings bindings = new SimpleBindings();

            Client a = new Client("server A");
            Client b = new Client("server B");
            Client c = new Client("server C");
            bindings.put("clients", Arrays.asList(a, b));

            Map<Client, String> map;
            map = new HashMap<Client, String>();
            map.put(a, "100.10.29.12");
            map.put(b, "100.10.29.13");
            map.put(c, "100.10.29.12");
            bindings.put("serverIp", map);

            bindings.put("ip", "100.10.29.12");

//            Object answer = compiledScript.eval(bindings);
//            System.out.println("acceptable servers : " + answer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        RuleDefinition def = RuleDefinition.createRuleDefinition("@ip=serverIp;");

        Server server = new Server(def);
        server.start();

        try {
            Proxy proxy = new Proxy(def);
            proxy.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
