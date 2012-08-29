package org.gajaba.rule.compiler.transformers;

import org.antlr.runtime.tree.Tree;
import org.gajaba.rule.compiler.SourceGenerator;

public class VariableTransformer implements TreeTransformer {

    @Override
    public void transform(Tree tree, StringBuilder builder, SourceGenerator generator) {
        System.out.println("var=" + tree.toStringTree());
        builder.append(tree.getChild(0).getText());
    }
}
