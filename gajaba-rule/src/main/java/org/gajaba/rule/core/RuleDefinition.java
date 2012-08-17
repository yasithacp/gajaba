package org.gajaba.rule.core;

import javax.script.*;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RuleDefinition {
    static ScriptEngine engine = new DSLEngine();
    private CompiledScript script;
    private SimpleBindings bindings = new SimpleBindings();


    private RuleDefinition(CompiledScript script) {
        this.script = script;
        bindings = new SimpleBindings();
        bindings.put("cache", Collections.emptyMap());
        bindings.put("agents", Collections.emptyList());


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

    public String evaluateRequest(AsynchronousSocketChannel client)  {
//        try {
//            Object answer = script.eval(bindings);
//            System.out.println("acceptable servers : " + answer);
//        } catch (ScriptException e) {
//            e.printStackTrace();
//        }
       Map o = (Map) bindings.get("cache");
        Iterator iterator = o.entrySet().iterator();
        if(iterator.hasNext()){
           return (String) ((Map.Entry)iterator.next()).getValue();
        }
        return null;
    }

    public void setCache(Map cache) {
        bindings.put("cache", cache);
    }

    public void setAgents(List<String> agents) {
        bindings.put("agents", agents);
    }
}
