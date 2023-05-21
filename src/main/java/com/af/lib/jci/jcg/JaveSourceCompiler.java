package com.af.lib.jci.jcg;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;

import com.af.lib.jci.jsg.api.JavaSource;

import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

public class JaveSourceCompiler {

	private static final JaveSourceCompiler instance = new JaveSourceCompiler();
	private JavaCompiler compiler;
	
	private JaveSourceCompiler() {
		this.compiler = ToolProvider.getSystemJavaCompiler();
	}
	
	public static JaveSourceCompiler getInstance() {
		return instance;
	}
	
	public boolean compile(JavaSource javaSource, String classOutput, DiagnosticCollector<JavaFileObject> diagnostics) {
		return compile(javaSource, new File(classOutput), diagnostics);
	}
	
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
