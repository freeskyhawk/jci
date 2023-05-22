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

public class Example2 {

	public static void main(String args[]) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		System.out.println("Start of Example 2");
		String sourceRoot1 = args[0];
		String classRoot1 = args[1];
		String code1 = 
				"package com.af.jci.examples;" + 
				"public class Example2HelloWorld {" +
				"	public Example2HelloWorld() {System.out.println(\"This is a hello world from a class Constructor generated on the fly !!\");}" +
				"}";

		String sourceRoot2 = args[2];
		String classRoot2 = args[3];
		String code2 = 
				"package com.af.jci.examples;" +
				"public class Example2HelloWorld {" +
				"	public Example2HelloWorld() {System.out.println(\"## Updated ## This is a hello world from a class Constructor generated on the fly !!\");}" +
				"}";

		createAndUseAJavaCodeOnTheFly(sourceRoot1, classRoot1, code1, sourceRoot2, classRoot2, code2);

		System.out.println("End of Example 2");
	}

	@SuppressWarnings("resource")
	private static void createAndUseAJavaCodeOnTheFly(
			String sourceRoot1, String classRoot1, String code1,
			String sourceRoot2, String classRoot2, String code2) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		JavaSource javaSource1 = new StringToJavaSourceAdapter(sourceRoot1).adapt(code1);
		JavaSource javaSource2 = new StringToJavaSourceAdapter(sourceRoot2).adapt(code2);

		DiagnosticCollector<JavaFileObject> diagnostics1 = new DiagnosticCollector<JavaFileObject>();
		DiagnosticCollector<JavaFileObject> diagnostics2 = new DiagnosticCollector<JavaFileObject>();

		boolean success1 = JaveSourceCompiler.getInstance().compile(javaSource1, classRoot1, diagnostics1);
		boolean success2 = JaveSourceCompiler.getInstance().compile(javaSource2, classRoot2, diagnostics2);

		if (!success1) {
			logDiagnostic(diagnostics1);
		}
		if (!success2) {
			logDiagnostic(diagnostics1);
		}
		ClassLoader classLoader1 = new ClassReloader(FileUtil.fileDirectoriesToUrls(classRoot1));
		ClassLoader classLoader2 = new ClassReloader(FileUtil.fileDirectoriesToUrls(classRoot2));
			if (javaSource1.getFullyQualifiedName().equals(javaSource2.getFullyQualifiedName())) {
				System.out.println("Same FQN (" + javaSource1.getFullyQualifiedName() + ")");
			}
			Class<?> loadedClass1 = classLoader1.loadClass(javaSource1.getFullyQualifiedName());
			Class<?> loadedClass2 = classLoader2.loadClass(javaSource2.getFullyQualifiedName());

			if (loadedClass1.equals(loadedClass2)) {
				System.out.println("Classes are the same !");
			} else {
				System.out.println("Classes are different !");
			}

		Object obj1 = loadedClass1.newInstance();
		Object obj2 = loadedClass2.newInstance();

		if (loadedClass2.isInstance(obj1)) {
			System.out.println("Can be assigned to this class type");
		}
		if (obj2.getClass().isAssignableFrom(loadedClass1)) {
			System.out.println("is assignable from this class type");
		}
		writeToDisk(javaSource1);
		writeToDisk(javaSource2);
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
