package com.af.lib.file;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class FileUtilTest {

	private String tempDirPath;
	
	@Before
	public void setUp() {
		this.tempDirPath = FileUtil.getTempDirectoryPath();
	}
	
	@Test
	public void tempDirCreatedIntempDirPath() {
		File tempDir = FileUtil.createTempDir("com_af_lib_file_FileUtilTest");
		assertThat(tempDirPath.startsWith(tempDir.getParent())).isTrue();
	}

	@Test
	public void tempDirDeletedOnExit() throws IOException {
		File tempDir = FileUtil.createTempDir("com_af_lib_file_FileUtilTest");
		File dir1 = new File(tempDir, "dir1");
		File file1 = new File(dir1, "file1.txt");
		file1.getParentFile().mkdirs();
		file1.createNewFile();
		
		FileUtil.recursiveDeleteOnExit(tempDir);
		
		assertThat(tempDir.exists()).isTrue();
	}
	
	@Test
	public void noConstructorAllowed() {
		assertThatExceptionOfType(InstantiationException.class).isThrownBy(() -> {new FileUtil();});
	}
}
