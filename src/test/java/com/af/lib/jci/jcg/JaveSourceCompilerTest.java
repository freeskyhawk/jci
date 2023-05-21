package com.af.lib.jci.jcg;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;

import org.junit.Before;
import org.junit.Test;

import com.af.lib.file.FileUtil;
import com.af.lib.jci.jcg.JaveSourceCompiler;
import com.af.lib.jci.jsg.api.JavaSource;
import com.af.lib.jci.jsg.impl.StringToJavaSourceAdapter;

public class JaveSourceCompilerTest {

	private JavaSource javaSource;
	
	private String sourceRoot;
	
	private String classRoot;
	
	private String code = 	"package com.af.test;\n" +
							"public class HelloWorld {\n" +
							"	public HelloWorld() {System.out.println(\"Bla !!\");}\n" +
							"}";

	private String dummyClassRoot;

	
	@Before
	public void setUp() throws IOException {
		createGenerationDirectoriesInTemp();
		this.javaSource = new StringToJavaSourceAdapter(sourceRoot).adapt(code);
	}

	private void createGenerationDirectoriesInTemp() throws IOException {
		File genDir = FileUtil.createTempDir("com_af_lib_jcl_JaveSourceCompilerTest");
		
		File sourceRootDir = new File(genDir, "src");
		sourceRootDir.mkdirs();
		this.sourceRoot = sourceRootDir.getPath();
		
		File classRootDir = new File(genDir, "bin");
		classRootDir.mkdirs();
		this.classRoot = classRootDir.getPath();
		
		File dummyClassRootDir = new File(genDir, "dummyBin");
		this.dummyClassRoot = dummyClassRootDir.getPath();
		
		FileUtil.recursiveDeleteOnExit(genDir);
	}
	
	@Test
	public void compilationSuccessTrue() {
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		boolean success = JaveSourceCompiler.getInstance().compile(javaSource, classRoot, diagnostics);
		
		assertThat(success).isTrue();
	}
	
	@Test
	public void compilationSuccessFalse() {
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		boolean success = JaveSourceCompiler.getInstance().compile(javaSource, dummyClassRoot, diagnostics);
		
		assertThat(success).isFalse();
	}
}
