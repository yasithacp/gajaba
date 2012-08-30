package org.gajaba.rule.compiler.transformers;

import org.antlr.runtime.tree.Tree;
import org.gajaba.rule.compiler.SourceGenerator;
import org.apache.commons.lang.StringEscapeUtils;

public class StringTransformer implements TreeTransformer {

    @Override
    public void transform(Tree tree, StringBuilder builder, SourceGenerator generator) {
        String unecaped = tree.getChild(0).getText();
        String ecaped = StringEscapeUtils.escapeJava(unecaped);
        builder.append("\"");
        builder.append(ecaped);
        builder.append("\"");
    }
}
