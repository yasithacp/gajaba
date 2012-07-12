package org.gajaba.rule.compiler;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public final class MemoryJavaFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {

    private Map<String, ByteArrayOutputStream> classNameToBytecodeMap = new HashMap<String, ByteArrayOutputStream>();

    public MemoryJavaFileManager(StandardJavaFileManager fileManager) {
        super(fileManager);
    }


    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind, FileObject sibling) {

        URI uri = null;
        try {
            uri = new URI(className);
        } catch (URISyntaxException e) {
            // valid class name should be a valid URI
        }
        ByteArrayOutputJavaFileObject outObj = new ByteArrayOutputJavaFileObject(uri);
        ByteArrayOutputStream outStream = (ByteArrayOutputStream) outObj.openOutputStream();
        classNameToBytecodeMap.put(className, outStream);
        return outObj;
    }

    @Override
    public JavaFileObject getJavaFileForInput(Location location, String className, Kind kind) {
        URI uri = null;
        try {
            uri = new URI(className);
        } catch (URISyntaxException e) {
            // valid class name should be a valid URI
        }
        return new StringInputJavaFileObject(uri);
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
