package com.algorithmfusion.libs.jci.jsg.api;

import java.io.IOException;

/**
 * A persistent capable java source abstraction.
 * 
 * @author Hallo Khaznadar
 */
public interface PersistentJavaSource {

	/**
	 * Persist the current JavaSource to the underlying storage.
	 *   
	 * @throws IOException
	 */
	void persist() throws IOException;
}