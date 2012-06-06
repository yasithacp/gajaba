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

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import javax.tools.*;
import javax.tools.JavaFileObject.Kind;
import java.lang.IllegalArgumentException;

public final class MemoryJavaFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {

    private Map<String, ByteArrayOutputStream> classNameToBytecodeMap = new HashMap<String, ByteArrayOutputStream>();

    public MemoryJavaFileManager(StandardJavaFileManager fileManager) {
        super(fileManager);
    }


    @Override
    public JavaFileObject getJavaFileForOutput(Location location,
                                               String className,
                                               Kind kind,
                                               FileObject sibling)
            throws IOException {
        URI uri = null;
        try {
            uri = new URI(className);
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
        JavaFileObject outObj = new ByteArrayOutputJavaFileObject(uri);
        ByteArrayOutputStream outStream = (ByteArrayOutputStream) outObj.openOutputStream();
        classNameToBytecodeMap.put(className, outStream);
        return outObj;
    }

    @Override
    public JavaFileObject getJavaFileForInput(Location location, String className, Kind kind) throws IOException {
        return new StringInputJavaFileObject(className);
    }

    public boolean classExists(String classname) {
        return classNameToBytecodeMap.containsKey(classname);
    }

    public byte[] getBytecode(String classname) {
        if (!classNameToBytecodeMap.containsKey(classname))
            throw new IllegalArgumentException("class " + classname + " not found in in-memory java file manager.");

        ByteArrayOutputStream outStream = classNameToBytecodeMap.get(classname);
        return outStream.toByteArray();
    }

}
