package org.gajaba.rule.compiler;

import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

public class ByteArrayOutputJavaFileObject extends SimpleJavaFileObject {

	private ByteArrayOutputStream out;
	
	public ByteArrayOutputJavaFileObject(URI uri) {
		super(uri, JavaFileObject.Kind.CLASS);
		out = new ByteArrayOutputStream();
	}

	@Override
	public OutputStream openOutputStream() {
		return out;
	}
}
