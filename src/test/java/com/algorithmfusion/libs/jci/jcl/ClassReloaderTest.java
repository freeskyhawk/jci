package com.algorithmfusion.libs.jci.jcl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.algorithmfusion.libs.file.FileUtil;
import com.algorithmfusion.libs.jci.jcg.JaveSourceCompiler;
import com.algorithmfusion.libs.jci.jsg.api.JavaSource;
import com.algorithmfusion.libs.jci.jsg.impl.StringToJavaSourceAdapter;

@RunWith(MockitoJUnitRunner.class)
public class ClassReloaderTest {

	private ClassLoader classLoader1;
	private ClassLoader classLoader2;
	private Class<?> loadedClass1;
	private Class<?> loadedClass2;
	
	private static JavaSource javaSource1;
	private static JavaSource javaSource2;
	
	private static String sourceRoot1;
	private static String classRoot1;
	private static String sourceRoot2;
	private static String classRoot2;
	
	@BeforeClass
	public static void setUp() throws IOException {
		String code1 = 
				"package com.algorithmfusion.jsg;" +
				"public class HelloWorld {" +
				"	public HelloWorld() {}" +
				"	public static String message() {return \"This is a hello world from a class Method generated on the fly !!\";}" +
				"}";
		
		String code2 = 
				"package com.algorithmfusion.jsg;" +
				"public class HelloWorld {" +
				"	public HelloWorld() {}" +
				"	public static String message() {return \"## Updated ## This is a hello world from a class Method generated on the fly !!\";}" +
				"}";
		
		createGenerationDirectoriesInTemp();
		
		javaSource1 = new StringToJavaSourceAdapter(sourceRoot1).adapt(code1);
		javaSource2 = new StringToJavaSourceAdapter(sourceRoot2).adapt(code2);
	}

	private static void createGenerationDirectoriesInTemp() throws IOException {
		File genDir = FileUtil.createTempDir("com.algorithmfusion.libs.jcl.ClassReloaderTest");
		
		File sourceRoot1Dir = new File(genDir, "src1");
		sourceRoot1Dir.mkdirs();
		sourceRoot1 = sourceRoot1Dir.getPath();
		
		File sourceRoot2Dir = new File(genDir, "src2");
		sourceRoot2Dir.mkdirs();
		sourceRoot2 = sourceRoot2Dir.getPath();
		
		File classRoot1Dir = new File(genDir, "bin1");
		classRoot1Dir.mkdirs();
		classRoot1 = classRoot1Dir.getPath();
		
		File classRoot2Dir = new File(genDir, "bin2");
		classRoot2Dir.mkdirs();
		classRoot2 = classRoot2Dir.getPath();
		
		FileUtil.recursiveDeleteOnExit(genDir);
	}
	
	@Test
	public void sameFQNclassCanBeReloaded() throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, MalformedURLException {
		
		DiagnosticCollector<JavaFileObject> diagnostics1 = new DiagnosticCollector<JavaFileObject>();
		boolean success1 = JaveSourceCompiler.getInstance().compile(javaSource1, classRoot1, diagnostics1);
		ClassReloader classReLoader = new ClassReloader();
		classReLoader.addURL(new File(classRoot1).toURI().toURL());
		classLoader1 = classReLoader;
		//classLoader1 = com.algorithmfusion.libs.jsg.api.JavaSource.class.getClassLoader();
		loadedClass1 = classLoader1.loadClass(javaSource1.getFullyQualifiedName());
		Object obj1 = loadedClass1.newInstance();
		String message1 = (String) loadedClass1.getDeclaredMethod("message").invoke(null);
		
		DiagnosticCollector<JavaFileObject> diagnostics2 = new DiagnosticCollector<JavaFileObject>();
		boolean success2 = JaveSourceCompiler.getInstance().compile(javaSource2, classRoot2, diagnostics2);
		classLoader2 = new ClassReloader(FileUtil.fileDirectoriesToUrls(classRoot2));
		loadedClass2 = classLoader2.loadClass(javaSource2.getFullyQualifiedName());
		Object obj2 = loadedClass2.newInstance();
		String message2 = (String) loadedClass2.getDeclaredMethod("message").invoke(null);

		String message3 = (String) loadedClass1.getDeclaredMethod("message").invoke(null);
		String message4 = (String) loadedClass2.getDeclaredMethod("message").invoke(null);
		
		Object obj3 = null;
		try {
			obj3 = loadedClass2.cast(obj1);
		} catch (ClassCastException e) {
			assertThat(obj3).isNull();
		}
		
		Object obj4 = null;
		try {
			obj4 = loadedClass1.cast(obj2);
		} catch (ClassCastException e) {		
			assertThat(obj4).isNull();
		}
		
		assertThat(success1).isTrue();
		assertThat(success2).isTrue();
		
		assertThat(javaSource1.getFullyQualifiedName()).isEqualTo(javaSource2.getFullyQualifiedName());
		
		assertThat(message1).isEqualTo("This is a hello world from a class Method generated on the fly !!");
		assertThat(message2).isEqualTo("## Updated ## This is a hello world from a class Method generated on the fly !!");
		
		assertThat(message1).isEqualTo(message3);
		assertThat(message2).isEqualTo(message4);
		
		assertThat(loadedClass1).isNotEqualTo(loadedClass2);
	}
	
	@Test
	public void illigalClassNameToReload() throws MalformedURLException {
		String badFormedClassName = "com.algorithmfusion.jsg.Hello World";
		classLoader2 = new ClassReloader(FileUtil.fileDirectoriesToUrls(classRoot2));
		try {
			loadedClass2 = null;
			loadedClass2 = classLoader2.loadClass(badFormedClassName);
		} catch (ClassNotFoundException e) {
			assertThat(loadedClass2).isNull();
		}
	}
	
	@Test
	public void illigalClassRootToReloaded() throws ClassNotFoundException, MalformedURLException {
		String className = "";
		String classRoot = "";
		classLoader2 = new ClassReloader(FileUtil.fileDirectoriesToUrls(classRoot));
		try {
			loadedClass2 = null;
			loadedClass2 = classLoader2.loadClass(className);
		} catch (ClassNotFoundException e) {
			assertThat(loadedClass2).isNull();
		}
	}
}
