package org.gajaba.rule.compiler.transformers;

import org.antlr.runtime.tree.Tree;
import org.gajaba.rule.compiler.SourceGenerator;
import org.gajaba.rule.parse.GajabaDSLLexer;

public class EqualOpTransformer implements TreeTransformer {

    @Override
    public void transform(Tree tree, StringBuilder builder, SourceGenerator generator) {
        System.out.println(tree.toStringTree());

        int typeLeft = tree.getChild(0).getType();
        int typeRight = tree.getChild(1).getType();

        if ((typeLeft == GajabaDSLLexer.INPUT_VAR) &&
                (typeRight == GajabaDSLLexer.INPUT_VAR)) {
            builder.append("        if(");
            generator.generateSubTree(tree.getChild(0), builder);
            builder.append(" != null && !");
            generator.generateSubTree(tree.getChild(0), builder);
            builder.append(".equals(");
            generator.generateSubTree(tree.getChild(1), builder);
            builder.append(")){\n");
            builder.append("            accepted.clear();\n");
            builder.append("        }\n");
        } else if (typeLeft == GajabaDSLLexer.STRING) {
            builder.append("        if(!");
            generator.generateSubTree(tree.getChild(0), builder);
            builder.append(".equals(");
            generator.generateSubTree(tree.getChild(1), builder);
            builder.append(")){\n");
            builder.append("            accepted.clear();\n");
            builder.append("        }\n");
        } else if (typeRight == GajabaDSLLexer.STRING) {
            builder.append("        if(!");
            generator.generateSubTree(tree.getChild(1), builder);
            builder.append(".equals(");
            generator.generateSubTree(tree.getChild(0), builder);
            builder.append(")){\n");
            builder.append("            accepted.clear();\n");
            builder.append("        }\n");
        } else if (typeLeft == GajabaDSLLexer.STATE_VAR) {
            generator.generateSubTree(tree.getChild(0), builder);
            builder.append(";\n");
        }
    }
}
