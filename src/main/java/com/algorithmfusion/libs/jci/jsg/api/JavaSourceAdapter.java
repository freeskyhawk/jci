package com.algorithmfusion.libs.jci.jsg.api;

/**
 * A generic adapter of any type to the {@link JavaSource}
 * 
 * @param <T>
 * 
 * @author Hallo Khaznadar
 */
public abstract class JavaSourceAdapter<T> {

	/**
	 * adapt (T) to {@link JavaSource}
	 * 
	 * @param source
	 * @return
	 */
	abstract public JavaSource adapt(T source);
}
