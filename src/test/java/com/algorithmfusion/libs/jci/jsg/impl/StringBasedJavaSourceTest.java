package com.algorithmfusion.libs.jci.jsg.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StringBasedJavaSourceTest {

	private StringBasedJavaSource stringBasedJavaSource;
	
	private String pkg = "com.algorithmfusion.test";
	
	private String name = "HelloWorld";
	
	private String code = 	"package com.algorithmfusion.test;\n" +
							"public class HelloWorld {\n" +
							"	public HelloWorld() {System.out.println(\"Bla !!\");}\n" +
							"}";
	@Before
	public void setUp() {
		this.stringBasedJavaSource = new StringBasedJavaSource.Builder().pkg(pkg).name(name).code(code).build();
	}
	
	@Test
	public void expectedPkg() {
		assertThat(stringBasedJavaSource.getPkg()).isEqualTo(pkg);
	}
	
	@Test
	public void expectedName() {
		assertThat(stringBasedJavaSource.getName()).isEqualTo(name);
	}
	
	@Test
	public void expectedCode() {
		assertThat(stringBasedJavaSource.getCode()).isEqualTo(code);
	}
	
	@Test
	public void expectedFullyQualifiedName() {
		assertThat(stringBasedJavaSource.getFullyQualifiedName()).isEqualTo("com.algorithmfusion.test.HelloWorld");
	}
	
	@Test
	public void expectedFullSourceNameWithPackage() {
		assertThat(stringBasedJavaSource.getFullSourceNameWithPackage()).isEqualTo("com/algorithmfusion/test/HelloWorld.java");
	}
	
	@Test
	public void expectedJavaFileObject() {
		assertThat(stringBasedJavaSource.getJavaFileObject()).isEqualTo(stringBasedJavaSource);
	}
	
	@Test
	public void expectedCharContent() {
		assertThat(stringBasedJavaSource.getCharContent(true)).isEqualTo(code);
		assertThat(stringBasedJavaSource.getCharContent(false)).isEqualTo(code);
	}
}
