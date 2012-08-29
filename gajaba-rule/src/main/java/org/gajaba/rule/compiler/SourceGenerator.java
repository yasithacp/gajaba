package org.gajaba.rule.compiler;

import org.antlr.runtime.tree.Tree;
import org.gajaba.rule.compiler.transformers.*;
import org.gajaba.rule.parse.GajabaDSLLexer;

import java.util.*;

public class SourceGenerator {

    private Set<Tree> variables;
    public static Map<TokenType, TreeTransformer> defaultTransformer = new HashMap<TokenType, TreeTransformer>();

    static {
        defaultTransformer.put(new TokenType(GajabaDSLLexer.OP, "="), new EqualOpTransformer());
        defaultTransformer.put(new TokenType(GajabaDSLLexer.INPUT_VAR, "INPUT_VAR"), new VariableTransformer());
        defaultTransformer.put(new TokenType(GajabaDSLLexer.STRING, "STRING"), new StringTransformer());
        defaultTransformer.put(new TokenType(GajabaDSLLexer.STATE_VAR, "STATE_VAR"), new CacheTransformer());
    }

    public String generate(Tree rootTree) {
        variables = getVariables(rootTree);

        StringBuilder builder = new StringBuilder();
        builder.append("package org.gajaba.rule.gen;\n");
        builder.append("import java.util.*;\n");
        builder.append("import org.gajaba.group.KeySeparator;\n");
        builder.append("import org.gajaba.rule.core.*;\n");
        builder.append("class CompiledDSLScript {\n");
        builder.append("    public static List<String> main (List<String> agents, Map<Object,String> cache, KeySeparator separator ");
        builder.append(generateParameters(variables));
        builder.append("){\n");


        builder.append("        List<String> accepted = new ArrayList<String>(agents);\n");
        for (int i = 0; i < rootTree.getChildCount(); i++) {
            Tree child = rootTree.getChild(i);
            generateSubTree(child, builder);
        }
        builder.append("        return (accepted);\n");
        builder.append("    }\n");
        builder.append("}\n");
        System.out.println(builder.toString());
        return builder.toString();
    }

    public void generateSubTree(Tree tree, StringBuilder builder) {
        TokenType tokenType = new TokenType(tree.getType(), tree.getText());
        TreeTransformer transformer = defaultTransformer.get(tokenType);
        if (transformer != null) {
            transformer.transform(tree, builder, this);
        } else {
            System.err.print("warning: no Transformer matching the sub tree " + tree.toStringTree());
        }
    }

    private String generateParameters(Set<Tree> variables) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Iterator<Tree> iterator = variables.iterator(); iterator.hasNext(); ) {
            Tree next = iterator.next();
            String text = next.getChild(0).getText();
            if (next.getType() == GajabaDSLLexer.INPUT_VAR) {
                stringBuilder.append(", ");
                stringBuilder.append("String ");
                stringBuilder.append(text);
            } else {
//                stringBuilder.append("Map<Object,String> ");
//                stringBuilder.append(text);
            }
        }
        return stringBuilder.toString();
    }

    public Set<Tree> getVariables() {
        return variables;
    }

    private static Set<Tree> getVariables(Tree ast) {
        HashSet<Tree> vars = new HashSet<Tree>();
        return getVariables(vars, ast);
    }

    private static Set<Tree> getVariables(Set<Tree> vars, Tree ast) {
        int childCount = ast.getChildCount();
        for (int i = 0; i < childCount; i++) {
            Tree child = ast.getChild(i);
            int type = ast.getType();
            if (type == GajabaDSLLexer.INPUT_VAR || type == GajabaDSLLexer.STATE_VAR) {
                vars.add(ast);
            } else {
                getVariables(vars, child);
            }
        }
        return vars;
    }
}
