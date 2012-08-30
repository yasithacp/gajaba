package org.gajaba.rule.compiler.transformers;

import org.antlr.runtime.tree.Tree;
import org.apache.commons.lang.StringEscapeUtils;
import org.gajaba.rule.compiler.SourceGenerator;

public class RegexTransformer implements TreeTransformer {

    @Override
    public void transform(Tree tree, StringBuilder builder, SourceGenerator generator) {
        builder.append(" CacheUtil.getRegex(");
        generator.generateSubTree(tree.getChild(2), builder);
        builder.append(",\"");
        String unescaped = tree.getChild(0).getText();
        String escaped = StringEscapeUtils.escapeJava(unescaped);
        builder.append(escaped);
        builder.append("\",");
        builder.append(tree.getChild(1).getText());
        builder.append(")");
    }
}
