package org.gajaba.rule.compiler.transformers;


import org.antlr.runtime.tree.Tree;

public interface TreeTransformer {
    void transform(Tree tree,StringBuilder builder);
}
