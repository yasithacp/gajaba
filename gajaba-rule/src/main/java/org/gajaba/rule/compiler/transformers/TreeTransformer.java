package org.gajaba.rule.compiler.transformers;


import org.antlr.runtime.tree.Tree;
import org.gajaba.rule.compiler.SourceGenerator;

public interface TreeTransformer {
    void transform(Tree tree, StringBuilder builder, SourceGenerator generator);
}
