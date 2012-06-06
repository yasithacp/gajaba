package org.gajaba.rule.compiler;

import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import java.io.*;
import java.net.URI;
import java.nio.CharBuffer;


public class StringInputJavaFileObject extends SimpleJavaFileObject {

	private StringWriter writer = new StringWriter();

	public StringInputJavaFileObject(URI uri) {
        super(uri, JavaFileObject.Kind.SOURCE);
    }

    @Override
    public Writer openWriter() throws IOException {
        return writer;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return CharBuffer.wrap(writer.toString());
    }
}
