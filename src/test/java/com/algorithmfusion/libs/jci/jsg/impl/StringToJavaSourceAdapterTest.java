package com.algorithmfusion.libs.jci.jsg.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.algorithmfusion.libs.jci.jsg.api.JavaSource;

@RunWith(MockitoJUnitRunner.class)
public class StringToJavaSourceAdapterTest {

	private StringToJavaSourceAdapter stringToJavaSourceAdapter;

	private String sourceRoot = "dummyRoot";
	@Before
	public void setUp() {
		this.stringToJavaSourceAdapter = new StringToJavaSourceAdapter(new File(sourceRoot));
	}

	private static void assertRootPkgName(JavaSource javaSource, String root, String pkg, String name) {
		assertThat(javaSource.getSourceRoot()).isEqualTo(root);
		assertThat(javaSource.getPkg()).isEqualTo(pkg);
		assertThat(javaSource.getName()).isEqualTo(name);
	}
	
	@Test
	public void codeExample01() {
		String sourceCode = "package com.algorithmfusion.jsg;\n" +
							"public class HelloWorld {\n" +
							"	public HelloWorld() {System.out.println(\"Bla !!\");}\n" +
							"}";
		
		JavaSource javaSource = stringToJavaSourceAdapter.adapt(sourceCode);
		
		assertRootPkgName(javaSource, sourceRoot, "com.algorithmfusion.jsg", "HelloWorld");
	}
	
	@Test
	public void codeExample02() {
		String sourceCode = "package com.algorithmfusion.jsg;"+
							"public class HelloWorld {" +
							"	public HelloWorld() {System.out.println(\"This is a hello world from a class Constructor generated on the fly !!\");}" +
							"	public static void message() {System.out.println(\"This is a hello world from a class Method generated on the fly !!\");}" +
							"	public static void kill() {System.exit(0);}" +
							"}";
		
		JavaSource javaSource = stringToJavaSourceAdapter.adapt(sourceCode);

		assertRootPkgName(javaSource, sourceRoot, "com.algorithmfusion.jsg", "HelloWorld");
	}
	
	@Test
	public void codeExample03() {
		String sourceCode = "package com.algorithmfusion.jsg;\n" +
							"/**\n" +
							" * class to create a class on the fly.\n" +
							" */\n" +
							"   public class HelloWorld {\n" +
							"	public HelloWorld() {System.out.println(\"Bla !!\");}\n" +
							"}";
		
		JavaSource javaSource = stringToJavaSourceAdapter.adapt(sourceCode);

		assertRootPkgName(javaSource, sourceRoot, "com.algorithmfusion.jsg", "HelloWorld");
	}
	
	@Test
	public void codeExample04() {
		String sourceCode = "package com.algorithmfusion.jsg;\n" +
							"/**\n" +
							" * public class Bla create a class on the fly.\n" +
							" */\n" +
							"   public class HelloWorld {\n" +
							"	public HelloWorld() {System.out.println(\"Bla !!\");}\n" +
							"}";
		
		JavaSource javaSource = stringToJavaSourceAdapter.adapt(sourceCode);

		assertRootPkgName(javaSource, sourceRoot, "com.algorithmfusion.jsg", "HelloWorld");
	}
	
	@Test
	public void codeExample05() {
		String sourceCode = "package com.algorithmfusion.jsg;\n" +
							"/**\n" +
							" * public class to create a class on the fly.\n" +
							" */\n" +
							"   class HelloWorld {\n" +
							"	public HelloWorld() {System.out.println(\"Bla !!\");}\n" +
							"}";
		
		JavaSource javaSource = stringToJavaSourceAdapter.adapt(sourceCode);

		assertRootPkgName(javaSource, sourceRoot, "com.algorithmfusion.jsg", "HelloWorld");
	}
	
	@Test
	public void codeExample06() {
		String sourceCode = "package com.algorithmfusion.jsg;\n" +
							"/**\n" +
							" * public class to create a class on the fly.\n" +
							" */\n" +
							"   abstract static class HelloWorld {\n" +
							"	public HelloWorld() {System.out.println(\"Bla !!\");}\n" +
							"}";
		
		JavaSource javaSource = stringToJavaSourceAdapter.adapt(sourceCode);

		assertRootPkgName(javaSource, sourceRoot, "com.algorithmfusion.jsg", "HelloWorld");
	}
	
	@Test
	public void codeExample07() {
		String sourceCode = "package com.algorithmfusion.jsg ;\n" +
							"/**\n" +
							" * public class to create a class on the fly.\n" +
							" */\n" +
							"   class HelloWorld \n{\n" +
							"	public HelloWorld() {System.out.println(\"Bla !!\");}\n" +
							"}";
		
		JavaSource javaSource = stringToJavaSourceAdapter.adapt(sourceCode);

		assertRootPkgName(javaSource, sourceRoot, "com.algorithmfusion.jsg", "HelloWorld");
	}
	
	@Test
	public void codeExample08() {
		String sourceCode = "  package com.algorithmfusion.jsg   ;\n" +
							"/**\n" +
							" * public class to {create a class on the fly.\n" +
							" */\n" +
							"   class HelloWorld \n{\n" +
							"	public HelloWorld() {System.out.println(\"Bla !!\");}\n" +
							"}";
		
		JavaSource javaSource = stringToJavaSourceAdapter.adapt(sourceCode);

		assertRootPkgName(javaSource, sourceRoot, "com.algorithmfusion.jsg", "HelloWorld");
	}
	
	@Test
	public void codeExample09() {
		String sourceCode = "  package com.algorithmfusion.jsg   ;\n" +
							"/* \n" +
							" public class to { create a class on the fly.\n" +
							" */\n" +
							"   class HelloWorld \n{\n" +
							"	public HelloWorld() {System.out.println(\"Bla !!\");}\n" +
							"}";
		
		JavaSource javaSource = stringToJavaSourceAdapter.adapt(sourceCode);

		assertRootPkgName(javaSource, sourceRoot, "com.algorithmfusion.jsg", "HelloWorld");
	}
	
	@Test
	public void codeExample10() {
		String sourceCode = "/**\n" +
							" * public classs to { create a class on the fly.\n" +
							" * this class has No package name ;.\n" +
							" */\n" +
							"   class HelloWorld \n{\n" +
							"	public HelloWorld() {System.out.println(\"Bla !!\");}\n" +
							"}";
		
		JavaSource javaSource = stringToJavaSourceAdapter.adapt(sourceCode);

		assertRootPkgName(javaSource, sourceRoot, "", "HelloWorld");
	}
	
	@Test
	public void codeExample11() {
		String sourceCode = "class HelloWorld \n{\n" +
							"	public HelloWorld() {System.out.println(\"Bla !!\");}\n" +
							"}";
		
		JavaSource javaSource = stringToJavaSourceAdapter.adapt(sourceCode);

		assertRootPkgName(javaSource, sourceRoot, "", "HelloWorld");
	}
}
