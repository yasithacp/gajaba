package org.gajaba.rule.core;


import org.antlr.runtime.*;
import org.antlr.runtime.tree.Tree;
import org.gajaba.rule.compiler.MemoryJavaFileManager;
import org.gajaba.rule.parse.GajabaDSLLexer;
import org.gajaba.rule.parse.GajabaDSLParser;

import javax.script.*;
import javax.tools.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DSLEngine extends AbstractScriptEngine implements Compilable {


    public static final String GENARATED_CLASS_NAME = "org.gajaba.rule.gen.CompiledDSLScript";
    private MemoryJavaFileManager fileManager;
    private JavaCompiler compiler;

    public DSLEngine() {
        compiler = ToolProvider.getSystemJavaCompiler();
        fileManager = new MemoryJavaFileManager(compiler.getStandardFileManager(null, null, null));
    }

    @Override
    public Object eval(String script, ScriptContext context) throws ScriptException {
        return null;
    }

    @Override
    public Object eval(Reader reader, ScriptContext context) throws ScriptException {
        return null;
    }

    @Override
    public Bindings createBindings() {
        return null;
    }

    @Override
    public ScriptEngineFactory getFactory() {
        return null;
    }

    @Override
    public CompiledScript compile(Reader script) throws ScriptException {
        try {
            return compile(new ANTLRReaderStream(script));
        } catch (IOException e) {
            throw new ScriptException(e);
        }
    }

    @Override
    public CompiledScript compile(String script) throws ScriptException {
        return compile(new ANTLRStringStream(script));
    }

    private CompiledScript compile(CharStream stream) throws ScriptException {
        GajabaDSLLexer lex = new GajabaDSLLexer(stream);
        GajabaDSLParser parser = new GajabaDSLParser(new CommonTokenStream(lex));
        try {
            GajabaDSLParser.dsl_doc_return root = parser.dsl_doc();
            Tree rootTree = (Tree) root.getTree();
            Set<Tree> variables = getVariables(rootTree);

            System.out.println(variables);
            compileJavaCode("").call();
            return null;
        } catch (RecognitionException e) {
            throw new ScriptException(e);
        }
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
                vars.add(child);
            } else {
                getVariables(vars, child);
            }
        }
        return vars;
    }


    private JavaCompiler.CompilationTask compileJavaCode(String javaCode) {

        JavaFileObject inputFileObj = fileManager.getJavaFileForInput(StandardLocation.CLASS_OUTPUT,
                GENARATED_CLASS_NAME, JavaFileObject.Kind.SOURCE);

        try {
            Writer inputWriter = inputFileObj.openWriter();
            inputWriter.write(javaCode);
        } catch (IOException e) {
        }

        List<JavaFileObject> compilationUnits = Arrays.asList(inputFileObj);
        List<String> options = Arrays.asList("-Xlint:all", "-g:none");


        DiagnosticListener<? super JavaFileObject> listener = new DiagnosticListener<JavaFileObject>() {
            @Override
            public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
                System.out.println(diagnostic);
            }

        };

        return compiler.getTask(null, fileManager, listener, options, null, compilationUnits);
    }

}
