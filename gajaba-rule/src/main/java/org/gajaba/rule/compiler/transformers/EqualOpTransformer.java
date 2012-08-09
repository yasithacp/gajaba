package org.gajaba.rule.compiler.transformers;

import org.antlr.runtime.tree.Tree;
import org.gajaba.rule.compiler.SourceGenerator;

public class EqualOpTransformer implements TreeTransformer {

    @Override
    public void transform(Tree tree, StringBuilder builder, SourceGenerator generator) {
        System.out.println(tree.toStringTree());
//        generator.generateSubTree(tree.getChild(0), builder);

//        for (Map.Entry<Client, String> entry : b.entrySet()) {
//            if(entry.getValue().equals(a)){
//                accepted.add(entry.getKey());
//            }
//        }

        builder.append("        for (Map.Entry<Client, String> entry : ");
        builder.append(tree.getChild(1).getChild(0));
        builder.append(".entrySet()) {\n");
        builder.append("           if(entry.getValue().equals(");
        builder.append(tree.getChild(0).getChild(0));
        builder.append(")){\n");
        builder.append("               accepted.add(entry.getKey());\n");
        builder.append("           }\n");
        builder.append("        }\n");

    }
}
