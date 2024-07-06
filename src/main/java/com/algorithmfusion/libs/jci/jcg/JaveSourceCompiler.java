package com.algorithmfusion.libs.jci.jcg;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;

import com.algorithmfusion.libs.jci.jsg.api.JavaSource;

import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

/**
 * A Singleton runtime compiler class using the {@link JavaSource} abstraction as an input.
 * 
 * @author Hallo Khaznadar
 */
public class JaveSourceCompiler {

	private static final JaveSourceCompiler instance = new JaveSourceCompiler();
	private JavaCompiler compiler;
	
	private JaveSourceCompiler() {
		this.compiler = ToolProvider.getSystemJavaCompiler();
	}
	
	/**
	 * Gets the instance of this class.
	 * 
	 * @return
	 */
	public static JaveSourceCompiler getInstance() {
		return instance;
	}
	
	/**
	 * Compiles the given {@link JavaSource} and produces the classes to the given classOutput location.
	 * This method also fills the given diagnostics information produced during the compilation process.
	 * 
	 * @param javaSource
	 * @param classOutput
	 * @param diagnostics
	 * @return
	 */
	public boolean compile(JavaSource javaSource, String classOutput, DiagnosticCollector<JavaFileObject> diagnostics) {
		return compile(javaSource, new File(classOutput), diagnostics);
	}
	
	/**
	 * Compiles the given {@link JavaSource} and produces the classes to the given classOutput location.
	 * This method also fills the given diagnostics information produced during the compilation process.
	 * 
	 * @param javaSource
	 * @param classOutput
	 * @param diagnostics
	 * @return
	 */
	public boolean compile(JavaSource javaSource, File classOutput, DiagnosticCollector<JavaFileObject> diagnostics) {
		StandardJavaFileManager javaFileManager = null;
		try {
			javaFileManager = createJavaFileManager(classOutput, diagnostics);
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
			return false;
		}

		Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(javaSource.getJavaFileObject());
		
		CompilationTask task = compiler.getTask(null, javaFileManager, diagnostics, null, null, compilationUnits);

		return task.call();
	}
	
	private StandardJavaFileManager createJavaFileManager(File classOutput,
			DiagnosticCollector<JavaFileObject> diagnostics) throws IOException {
		StandardJavaFileManager javaFileManager = compiler.getStandardFileManager(diagnostics, null, null);
		
		javaFileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(classOutput));
		return javaFileManager;
	}
}
