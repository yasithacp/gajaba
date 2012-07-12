package org.gajaba.rule.core;


import org.antlr.runtime.*;
import org.antlr.runtime.tree.Tree;
import org.gajaba.rule.compiler.GajabaDSLCompliedScript;
import org.gajaba.rule.compiler.MemoryJavaFileManager;
import org.gajaba.rule.compiler.MemoryJavaFileManagerClassLoader;
import org.gajaba.rule.compiler.SourceGenerator;
import org.gajaba.rule.parse.GajabaDSLLexer;
import org.gajaba.rule.parse.GajabaDSLParser;

import javax.script.*;
import javax.tools.*;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.*;

public class DSLEngine extends AbstractScriptEngine implements Compilable, Invocable {


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

    @Override
    public Object invokeMethod(Object thiz, String name, Object... args) throws ScriptException, NoSuchMethodException {
        return null;//TODO
    }

    @Override
    public <T> T getInterface(Class<T> clasz) {
        return null;//TODO
    }

    @Override
    public <T> T getInterface(Object thiz, Class<T> clasz) {
        return null;//TODO
    }


    private CompiledScript compile(CharStream stream) throws ScriptException {
        GajabaDSLLexer lex = new GajabaDSLLexer(stream);
        GajabaDSLParser parser = new GajabaDSLParser(new CommonTokenStream(lex));
        try {
            GajabaDSLParser.dsl_doc_return root = parser.dsl_doc();
            Tree rootTree = (Tree) root.getTree();

            SourceGenerator generator = new SourceGenerator();

            JavaCompiler.CompilationTask compilationTask = compileJavaCode(generator.generate(rootTree));
            if (compilationTask.call()) {
                return new GajabaDSLCompliedScript( this,generator.getVariables());
            } else {
                System.out.println("error in compiling");
                return null;
            }
        } catch (RecognitionException e) {
            throw new ScriptException(e);
        }
    }


    public Object invokeFunction(String name, Class classes[], Object[] args) throws ScriptException, NoSuchMethodException {
        MemoryJavaFileManagerClassLoader classLoader = new MemoryJavaFileManagerClassLoader(fileManager);
        try {
            Class compiledClass = classLoader.loadClass(GENARATED_CLASS_NAME);
            Method method = compiledClass.getMethod(name, classes);
            method.setAccessible(true);
            return method.invoke(null, args);
        } catch (ClassNotFoundException classNotFoundEx) {
            throw new NoSuchMethodException();
        } catch (NoSuchMethodException noSuchMethodEx) {
            throw noSuchMethodEx;
        } catch (Exception e) {
            throw new ScriptException(e);
        }
    }

    @Override
    public Object invokeFunction(String name, Object... args) throws ScriptException, NoSuchMethodException {
        Class classes[] = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            classes[i] = args[i].getClass();
        }
        return invokeFunction(name, classes, args);
    }



    private JavaCompiler.CompilationTask compileJavaCode(String javaCode) {

        JavaFileObject inputFileObj = fileManager.getJavaFileForInput(StandardLocation.CLASS_OUTPUT,
                GENARATED_CLASS_NAME, JavaFileObject.Kind.SOURCE);

        try {
            Writer inputWriter = inputFileObj.openWriter();
            inputWriter.write(javaCode);
        } catch (IOException e) {
            //we are writing to memory, io error is unlikely.
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
