package com.af.lib.jci.jcl;

import java.net.URL;
import java.net.URLClassLoader;

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
