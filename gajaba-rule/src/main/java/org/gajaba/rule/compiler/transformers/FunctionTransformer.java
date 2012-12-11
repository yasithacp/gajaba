package org.gajaba.rule.compiler.transformers;

import org.antlr.runtime.tree.Tree;
import org.gajaba.group.KeySeparator;
import org.gajaba.rule.compiler.SourceGenerator;

import java.util.HashMap;
import java.util.Map;

public class FunctionTransformer implements TreeTransformer {

    public static Map<String, String> functionNames = new HashMap<String, String>();

    static {
        functionNames.put("min", "MinFunction");
    }

    @Override
    public void transform(Tree tree, StringBuilder builder, SourceGenerator generator) {
        String name = tree.getChild(0).getText();
        String className = functionNames.get(name);
        builder.append("        accepted = ");
        builder.append(className);
        builder.append(".execute(\"");
        builder.append(tree.getChild(1).getChild(0).getChild(0).getText());
        builder.append("\", agents, cache, separator);\n");
    }
}
