package com.algorithmfusion.libs.jci.jsg.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.algorithmfusion.libs.file.FileUtil;

@RunWith(MockitoJUnitRunner.class)
public class FileBasedJavaSourceTest {
	
	private FileBasedJavaSource fileBasedJavaSource;
	
	private String sourceRoot = "gen/src";
	
	private String pkg = "com.algorithmfusion.test";
	
	private String name = "HelloWorld";
	
	private String code = 	"package com.algorithmfusion.test;\n" +
							"public class HelloWorld {\n" +
							"	public HelloWorld() {System.out.println(\"Bla !!\");}\n" +
							"}";
	@Before
	public void setUp() {
		this.fileBasedJavaSource = new FileBasedJavaSource.Builder().sourceRoot(sourceRoot).pkg(pkg).name(name).code(code).build();
	}
	
	@Test
	public void expectedSourceRoot() {
		assertThat(fileBasedJavaSource.getSourceRoot()).isEqualTo(sourceRoot);
	}
	
	@Test
	public void expectedFile() {
		File file = fileBasedJavaSource.file();
		String expectedParent = "gen" + File.separator + "src" + File.separator + "com" + File.separator + "algorithmfusion" + File.separator + "test";

		assertThat(file).isNotNull();
		assertThat(file.getParent()).isEqualTo(expectedParent);
		assertThat(file.getName()).isEqualTo("HelloWorld.java");
	}
	
	@Test
	public void expectedPersist() throws IOException {
		File tempDir = FileUtil.createTempDir("com_af_lib_jsg_impl");
		String sourceRoot = tempDir.getPath();
		this.fileBasedJavaSource = new FileBasedJavaSource.Builder().sourceRoot(sourceRoot).pkg(pkg).name(name).code(code).build();
		File file = fileBasedJavaSource.file();
		fileBasedJavaSource.persist();
		FileUtil.recursiveDeleteOnExit(tempDir);
		String expectedParent = sourceRoot + File.separator + "com" + File.separator + "algorithmfusion" + File.separator + "test";
		
		assertThat(file).isNotNull();
		assertThat(file.getParent()).isEqualTo(expectedParent);
		assertThat(file.getName()).isEqualTo("HelloWorld.java");
		assertThat(file.exists()).isTrue();
	}
}
