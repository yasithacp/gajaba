package org.gajaba.rule.core;

import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import org.gajaba.rule.parse.*;

import javax.script.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception {
        String src = "@a=b;x>d;";
        ScriptEngine engine = new DSLEngine();

        try {
            Compilable compiler = (Compilable) engine;
            CompiledScript compiledScript = compiler.compile(src);
//            Bindings bindings = new SimpleBindings();
//            bindings.put("a", new Boolean(true));
//            bindings.put("b", new Boolean(true));
//            Object answer = compiledScript.eval(bindings);
//            System.out.println(answer);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
