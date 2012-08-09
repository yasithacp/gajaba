package org.gajaba.rule.core;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RuleDefinition {
    static ScriptEngine engine = new DSLEngine();
    private CompiledScript script;
    private Map cache = Collections.emptyMap();
    private List<String> agents;


    private RuleDefinition(CompiledScript script) {
        this.script = script;
    }

    public static RuleDefinition createRuleDefinition(String ruleStr) {
        try {
            Compilable compiler = (Compilable) engine;
            CompiledScript compiledScript = compiler.compile(ruleStr);
            return new RuleDefinition(compiledScript);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void evaluateRequest() {
    }

    public void setCache(Map cache) {
        this.cache = cache;
    }

    public void setAgents(List<String> agents) {
        this.agents = agents;
    }
}
