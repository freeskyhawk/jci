package com.algorithmfusion.libs.jci.jcl;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * This class can load and/or reload an already loaded class.
 * 
 * @author Hallo Khaznadar
 */
public class ClassReloader extends URLClassLoader {
	
	public ClassReloader() {
		this(new URL[] {});
	}
	public ClassReloader(URL[] urls) {
		super(urls);
	}

	@Override
	public void addURL(URL url) {
		super.addURL(url);
	}
}
