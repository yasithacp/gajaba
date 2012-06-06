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

public class MemoryJavaFileManagerClassLoader extends ClassLoader {
    
	private MemoryJavaFileManager fileManager;
    
    public MemoryJavaFileManagerClassLoader(MemoryJavaFileManager fileManager) {
        this.fileManager = fileManager;
    }

	/* (non-Javadoc)
	 * @see java.lang.ClassLoader#loadClass(java.lang.String)
	 */
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
