package com.algorithmfusion.libs.jci.jsg.api;

import java.io.File;

/**
 * A File based java source abstraction.
 * 
 * @author Hallo Khaznadar
 */
public interface FileJavaSource {
	String getSourceRoot();
	File file();
}
