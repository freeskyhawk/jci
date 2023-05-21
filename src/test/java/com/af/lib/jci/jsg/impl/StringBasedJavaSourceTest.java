package com.af.lib.jci.jsg.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.af.lib.jci.jsg.impl.StringBasedJavaSource;

@RunWith(MockitoJUnitRunner.class)
public class StringBasedJavaSourceTest {

	private StringBasedJavaSource stringBasedJavaSource;
	
	private String pkg = "com.af.test";
	
	private String name = "HelloWorld";
	
	private String code = 	"package com.af.test;\n" +
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
		assertThat(stringBasedJavaSource.getFullyQualifiedName()).isEqualTo("com.af.test.HelloWorld");
	}
	
	@Test
	public void expectedFullSourceNameWithPackage() {
		assertThat(stringBasedJavaSource.getFullSourceNameWithPackage()).isEqualTo("com/af/test/HelloWorld.java");
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
