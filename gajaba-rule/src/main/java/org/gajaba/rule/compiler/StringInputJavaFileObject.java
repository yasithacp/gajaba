/**
 * <copyright>
 *
 * Copyright (c) 2006 Chaur G. Wu. All rights reserved. 
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as 
 * published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * </copyright>
 */

package org.gajaba.rule.compiler;

import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.CharBuffer;


public class StringInputJavaFileObject extends SimpleJavaFileObject {

	private StringWriter writer = null;
	
	public StringInputJavaFileObject(String name) {
        super(name, JavaFileObject.Kind.SOURCE);
        writer = new StringWriter();
    }
	
	
	/* (non-Javadoc)
	 * @see net.sf.model4lang.boolscript.engine.SimpleJavaFileObject#openWriter()
	 */
	@Override
	public Writer openWriter() throws IOException, IllegalStateException, UnsupportedOperationException {
		return writer;
	}


	/* (non-Javadoc)
	 * @see javax.tools.JavaFileObject#openReader()
	 */
	@Override
	public Reader openReader() throws IOException, IllegalStateException, UnsupportedOperationException {
		return new StringReader(writer.toString());
	}

	
	/* (non-Javadoc)
	 * @see net.sf.model4lang.boolscript.engine.SimpleJavaFileObject#getCharContent(boolean)
	 */
	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException, IllegalStateException, UnsupportedOperationException {
		 return CharBuffer.wrap(writer.toString());
	}

}
