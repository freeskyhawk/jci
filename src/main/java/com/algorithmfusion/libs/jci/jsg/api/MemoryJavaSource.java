package com.algorithmfusion.libs.jci.jsg.api;

import javax.tools.JavaFileObject;

/**
 * A memory based java source abstraction.
 * 
 * @author Hallo Khaznadar
 */
public interface MemoryJavaSource {
	
	/**
	 * getFullyQualifiedName
	 * @return
	 */
	String getFullyQualifiedName();
	
	/**
	 * getFullSourceNameWithPackage
	 * @return
	 */
	String getFullSourceNameWithPackage();
	
	/**
	 * getPkg
	 * 
	 * @return
	 */
	String getPkg();
	
	/**
	 * getName
	 * @return
	 */
	String getName();
	
	/**
	 * getCode
	 * 
	 * @return
	 */
	String getCode();
	
	/**
	 * getJavaFileObject
	 * 
	 * @return
	 */
	JavaFileObject getJavaFileObject();
}
