
package org.gajaba.rule.compiler;

public class MemoryJavaFileManagerClassLoader extends ClassLoader {
    
	private MemoryJavaFileManager fileManager;
    
    public MemoryJavaFileManagerClassLoader(MemoryJavaFileManager fileManager) {
        this.fileManager = fileManager;
    }

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		try {
            return super.loadClass(name);
        } catch (ClassNotFoundException e) {
        	if (!fileManager.classExists(name))
        		throw e;
        	
            byte[] bytecode = fileManager.getBytecode(name);
            return defineClass(name, bytecode, 0, bytecode.length);
        }
	}
}
