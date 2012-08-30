package org.gajaba.rule.core;

import org.gajaba.group.GMSSeparator;
import org.gajaba.group.GroupManager;

import javax.script.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RuleDefinition {
    static ScriptEngine engine = new DSLEngine();
    private CompiledScript script;
    private SimpleBindings bindings = new SimpleBindings();
    private Map cache;
    private List<String> agents;
    private GMSSeparator separator;


    private RuleDefinition(CompiledScript script, GMSSeparator separator) {
        this.script = script;
        bindings = new SimpleBindings();

        cache = Collections.emptyMap();
        bindings.put("cache", cache);
        agents = Collections.emptyList();
        bindings.put("agents", agents);
        this.separator = separator;
        bindings.put("separator", separator);
    }

    public static RuleDefinition createRuleDefinition(String ruleStr, GMSSeparator separator) {
        try {
            Compilable compiler = (Compilable) engine;
            CompiledScript compiledScript = compiler.compile(ruleStr);
            return new RuleDefinition(compiledScript, separator);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String evaluateRequest(AsynchronousSocketChannel client, String url) {
        bindings.put("url", url);
        try {
            InetAddress address = ((InetSocketAddress) client.getRemoteAddress()).getAddress();
            bindings.put("ip", address.toString());
        } catch (IOException e) {
        }
        try {
            List<String> answer = (List<String>) script.eval(bindings);
            if (answer.size() > 0) {
                String ip = (String) cache.get(separator.construct(GroupManager.GROUP_NAME, answer.get(0), "ip"));
                return ip;
            }
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setCache(Map cache) {
        this.cache = cache;
        bindings.put("cache", cache);
    }

    public void setAgents(List<String> agents) {
        agents.remove("GAJABA_SERVER");
        this.agents = agents;
        bindings.put("agents", agents);
    }
}
