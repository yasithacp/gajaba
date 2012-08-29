package org.gajaba.rule.compiler;

import org.antlr.runtime.tree.Tree;
import org.gajaba.group.KeySeparator;
import org.gajaba.rule.core.DSLEngine;
import org.gajaba.rule.parse.GajabaDSLLexer;

import javax.script.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GajabaDSLCompliedScript extends CompiledScript {

    public static final String AGENTS = "agents";
    public static final String CACHE = "cache";
    public static final String SEPARATOR = "separator";
    private DSLEngine engine;
    private Set<Tree> variables;
    private Class classes[];
    private List<String> stateVariables;
    private List<String> inputVariables;

//    public List<String> getStateVariables() {
//        if (stateVariables == null) {
//            genVarLists();
//        }
//        return stateVariables;
//    }

    public List<String> getInputVariables() {
        if (inputVariables == null) {
            genVarLists();
        }
        return inputVariables;
    }

    private void genVarLists() {
        inputVariables = new ArrayList<>();
        stateVariables = new ArrayList<>();
        for (Tree variable : variables) {
            if (variable.getType() == GajabaDSLLexer.STATE_VAR) {
                stateVariables.add(variable.getChild(0).getText());
            } else if (variable.getType() == GajabaDSLLexer.INPUT_VAR) {
                inputVariables.add(variable.getChild(0).getText());
            }
        }
    }

    public GajabaDSLCompliedScript(DSLEngine engine, Set<Tree> vars) {
        this.engine = engine;
        this.variables = vars;

        int i = 3;
        classes = new Class[getInputVariables().size() + 3];
        classes[0] = List.class;
        classes[1] = Map.class;
        classes[2] = KeySeparator.class;
        for (String next : inputVariables) {
            classes[i++] = String.class;
        }
    }

    @Override
    public Object eval(ScriptContext context) throws ScriptException {
        Bindings bindings = context.getBindings(ScriptContext.ENGINE_SCOPE);
        return eval(bindings);
    }

    @Override
    public Object eval(Bindings bindings) throws ScriptException {

        List arguments = new ArrayList();

        arguments.add(bindings.get(AGENTS));
        arguments.add(bindings.get(CACHE));
        arguments.add(bindings.get(SEPARATOR));
        for (Tree next : variables) {
            if (next.getType() == GajabaDSLLexer.INPUT_VAR)
                arguments.add(bindings.get(next.getChild(0).getText()));
        }

        try {
            return engine.invokeFunction("main", classes, arguments.toArray());
        } catch (NoSuchMethodException e) {
            throw new ScriptException(e);
        }
    }

    @Override
    public Object eval() throws ScriptException {
        Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
        return eval(bindings);
    }

    @Override
    public ScriptEngine getEngine() {
        return engine;
    }

}
