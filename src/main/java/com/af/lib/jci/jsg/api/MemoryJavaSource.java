package com.af.lib.jci.jsg.api;

import javax.tools.JavaFileObject;

public interface MemoryJavaSource {
	String getFullyQualifiedName();
	String getFullSourceNameWithPackage();
	String getPkg();
	String getName();
	String getCode();
	JavaFileObject getJavaFileObject();
}
