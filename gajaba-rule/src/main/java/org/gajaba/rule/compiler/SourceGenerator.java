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
        defaultTransformer.put(new TokenType(GajabaDSLLexer.REGEX, "REGEX"), new RegexTransformer());
        defaultTransformer.put(new TokenType(GajabaDSLLexer.FUNCTION, "FUNCTION"), new FunctionTransformer());
    }

    /**
     * Class generator
     * @param rootTree
     * @return
     */
    public String generate(Tree rootTree) {
        variables = getVariables(rootTree);

        StringBuilder builder = new StringBuilder();
        builder.append("package org.gajaba.rule.gen;\n");
        builder.append("import java.util.*;\n");
        builder.append("import org.gajaba.rule.compiler.functions.*;\n");
        builder.append("import org.gajaba.group.KeySeparator;\n");
        builder.append("import org.gajaba.rule.core.*;\n");
        builder.append("class CompiledDSLScript {\n");


        for (int i = 0; i < rootTree.getChildCount(); i++) {
            Tree set = rootTree.getChild(i);
            builder.append("    public static List<String> getSet");
            builder.append(i);
            builder.append("AcceptList (List<String> agents, Map<Object,String> cache, KeySeparator separator ");
            builder.append(generateParameters(variables));
            builder.append("){\n");
            builder.append("        List<String> accepted = new ArrayList<String>(agents);\n");
            for (int j = 0; j < set.getChildCount(); j++) {
                Tree rule = set.getChild(j);
                generateSubTree(rule, builder);
            }
            builder.append("        return (accepted);\n");
            builder.append("    }\n");
        }


        builder.append("    public static List<String> main (List<String> agents, Map<Object,String> cache, KeySeparator separator ");
        builder.append(generateParameters(variables));
        builder.append("){\n");
        builder.append("        Set<String> accepted = new TreeSet<String>();\n");
        for (int i = 0; i < rootTree.getChildCount(); i++) {
            TreeSet<String> accepted = new TreeSet<String>();

            builder.append("        accepted.addAll(getSet");
            builder.append(i);
            builder.append("AcceptList(agents, cache, separator");
            builder.append(generateParameterValues(variables));
            builder.append("));\n");
        }
        builder.append("        return (new ArrayList<String>(accepted));\n");
        builder.append("    }\n");

        builder.append("}\n");
        System.out.println(builder.toString());
        return builder.toString();
    }

    /**
     * Generate Sub Tree
     * @param tree
     * @param builder
     */
    public void generateSubTree(Tree tree, StringBuilder builder) {
        TokenType tokenType = new TokenType(tree.getType(), tree.getText());
        TreeTransformer transformer = defaultTransformer.get(tokenType);
        if (transformer != null) {
            transformer.transform(tree, builder, this);
        } else {
            System.err.print("warning: no Transformer matching the sub tree " + tree.toStringTree());
        }
    }

    /**
     * Generate Parameters
     * @param variables
     * @return
     */
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

    private String generateParameterValues(Set<Tree> variables) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Iterator<Tree> iterator = variables.iterator(); iterator.hasNext(); ) {
            Tree next = iterator.next();
            String text = next.getChild(0).getText();
            if (next.getType() == GajabaDSLLexer.INPUT_VAR) {
                stringBuilder.append(", ");
                stringBuilder.append(text);
            } else {
//                stringBuilder.append("Map<Object,String> ");
//                stringBuilder.append(text);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Get Variables
     * @return
     */
    public Set<Tree> getVariables() {
        return variables;
    }

    /**
     * Get Variables
     * @param ast
     * @return
     */
    private static Set<Tree> getVariables(Tree ast) {
        HashSet<Tree> vars = new HashSet<Tree>();
        return getVariables(vars, ast);
    }

    /**
     * Get Variables
     * @param vars
     * @param ast
     * @return
     */
    private static Set<Tree> getVariables(Set<Tree> vars, Tree ast) {
        int childCount = ast.getChildCount();
        for (int i = 0; i < childCount; i++) {
            Tree child = ast.getChild(i);
            int type = ast.getType();
            if (type == GajabaDSLLexer.INPUT_VAR || type == GajabaDSLLexer.STATE_VAR) {
                vars.add(ast);
            }
            getVariables(vars, child);
        }
        return vars;
    }
}
