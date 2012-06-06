package org.gajaba.rule.compiler;

import org.gajaba.rule.core.DSLEngine;

import javax.script.*;
import javax.tools.JavaCompiler;

public class GajabaDSLCompliedScript extends CompiledScript {

	private JavaCompiler.CompilationTask task;
    private DSLEngine engine;

	public GajabaDSLCompliedScript( JavaCompiler.CompilationTask task){
		this.task = task;
	}
	
	@Override
	public Object eval(ScriptContext context) throws ScriptException {
		Bindings bindings = context.getBindings(ScriptContext.ENGINE_SCOPE);
		return eval(bindings);
	}

	@Override
	public Object eval(Bindings bindings) throws ScriptException {
		Invocable invocable = (Invocable) engine;


		try {
			return invocable.invokeFunction("main");
		}
		catch (NoSuchMethodException e) {
			throw new ScriptException(e);
		}
	}
	
	@Override
	public Object eval() throws ScriptException {
		Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
		return eval(bindings);
	}
	
	@Override
	public ScriptEngine getEngine() {
		return engine;
	}

}
