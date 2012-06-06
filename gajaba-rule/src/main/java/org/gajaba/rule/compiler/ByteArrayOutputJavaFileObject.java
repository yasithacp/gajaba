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
