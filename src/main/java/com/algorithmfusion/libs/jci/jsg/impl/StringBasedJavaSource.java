package com.algorithmfusion.libs.jci.jsg.impl;

import java.net.URI;

import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;

import com.algorithmfusion.libs.jci.jsg.api.MemoryJavaSource;

/**
 * A {@link String} based in memory java source object.
 * You can simply define any java source class with it by passing the different java class parts as {@link String} values through its Builder.
 *  
 * @author Hallo Khaznadar
 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFullyQualifiedName() {
		return getFullyQualifiedName(pkg, name);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFullSourceNameWithPackage() {
		return getFullSourceNameWithPackage(pkg, name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPkg() {
		return pkg;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCode() {
		return code;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JavaFileObject getJavaFileObject() {
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 */
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
