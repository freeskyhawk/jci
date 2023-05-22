package com.af.lib.jci.examples;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;

import com.af.lib.file.FileUtil;
import com.af.lib.jci.jcg.JaveSourceCompiler;
import com.af.lib.jci.jcl.ClassReloader;
import com.af.lib.jci.jsg.api.JavaSource;
import com.af.lib.jci.jsg.impl.StringToJavaSourceAdapter;

public class Example1 {
	
	public static void main(String args[]) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		System.out.println("Start of Example 1");
		String sourceRoot = args[0];
		String classRoot = args[1];
		String code = 
				"package com.af.jci.examples;" +
				"public class Example1HelloWorld {" +
				"	public Example1HelloWorld() {System.out.println(\"This is a hello world from a class Constructor generated on the fly !!\");}" +
				"}";
		
		JavaSource javaSource = new StringToJavaSourceAdapter(sourceRoot).adapt(code);
		
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		
		boolean success = JaveSourceCompiler.getInstance().compile(javaSource, classRoot, diagnostics);
		
		if (!success) {
			logDiagnostic(diagnostics);
		}
		@SuppressWarnings("resource")
		ClassLoader classLoader = new ClassReloader(FileUtil.fileDirectoriesToUrls(classRoot));
		Class<?> loadedClass = classLoader.loadClass(javaSource.getFullyQualifiedName());
		
		loadedClass.newInstance();

		
		writeToDisk(javaSource);
		
		System.out.println("End of Example 1");
	}
	
	private static void writeToDisk(JavaSource javaSource) {
		System.out.println("Writing (" + javaSource.file().getAbsolutePath() + ") to disk ...");
		try {
			javaSource.persist();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void logDiagnostic(DiagnosticCollector<JavaFileObject> diagnostics) {
		for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
			System.out.println(diagnostic);
		}
	}
}
