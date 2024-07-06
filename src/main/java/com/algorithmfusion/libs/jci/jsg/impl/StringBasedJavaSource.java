package com.algorithmfusion.libs.jci.jsg.impl;

import java.net.URI;

import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;

import com.algorithmfusion.libs.jci.jsg.api.MemoryJavaSource;

public class StringBasedJavaSource extends SimpleJavaFileObject implements MemoryJavaSource {

	private String pkg;

	private String name;

	private String code;

	protected StringBasedJavaSource(Builder builder) {
		super(URI.create("file:///" + getFullSourceNameWithPackage(builder.pkg, builder.name)), Kind.SOURCE);
		this.pkg = builder.pkg;
		this.name = builder.name;
		this.code = builder.code;
	}

	private static String getFullyQualifiedName(String pkg, String name) {
		return pkg + "." + name;
	}

	private static String getFullSourceNameWithPackage(String pkg, String name) {
		return getFullyQualifiedName(pkg, name).replace('.', '/') + Kind.SOURCE.extension;
	}

	@Override
	public String getFullyQualifiedName() {
		return getFullyQualifiedName(pkg, name);
	}

	@Override
	public String getFullSourceNameWithPackage() {
		return getFullSourceNameWithPackage(pkg, name);
	}

	@Override
	public String getPkg() {
		return pkg;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public JavaFileObject getJavaFileObject() {
		return this;
	}
	
	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) {
		return code;
	}

	public static class Builder {

		private String pkg;

		private String name;

		private String code;

		public Builder() {
		}

		public Builder pkg(String pkg) {
			this.pkg = pkg;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder code(String code) {
			this.code = code;
			return this;
		}

		public StringBasedJavaSource build() {
			return new StringBasedJavaSource(this);
		}
	}
}
