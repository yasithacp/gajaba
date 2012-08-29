package org.gajaba.rule.compiler.transformers;

import org.antlr.runtime.tree.Tree;
import org.gajaba.rule.compiler.SourceGenerator;

import java.util.Map;

public class CacheTransformer implements TreeTransformer {

    @Override
    public void transform(Tree tree, StringBuilder builder, SourceGenerator generator) {
        builder.append("CacheUtil.getCacheSubMap(");
        generator.generateSubTree(tree.getChild(0),builder);
        builder.append(", cache)");
    }
}
