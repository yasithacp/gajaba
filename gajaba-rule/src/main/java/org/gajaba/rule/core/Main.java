package org.gajaba.rule.core;

import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import org.gajaba.rule.parse.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String src = "a=b;x>d;";
        new GajabaDSLLexer(new ANTLRStringStream(src))  ;
        GajabaDSLLexer lex = new GajabaDSLLexer(new ANTLRStringStream(src));

        GajabaDSLParser parser = new GajabaDSLParser(new CommonTokenStream(lex));
        GajabaDSLParser.dsl_doc_return root = parser.dsl_doc();
        Tree rootTree = (Tree) root.getTree();

        System.out.println(rootTree.getChild(0).getType());

    }
}
