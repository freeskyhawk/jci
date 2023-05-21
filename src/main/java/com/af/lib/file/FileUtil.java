package com.af.lib.file;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class FileUtil {

	protected FileUtil() throws InstantiationException {
	    throw new InstantiationException("Instances of this type are forbidden.");
	}
	
	public static void recursiveDeleteOnExit(File file) throws IOException {
		recursiveDeleteOnExit(file.toPath());
	}
	
	public static void recursiveDeleteOnExit(Path path) throws IOException {
		Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
				file.toFile().deleteOnExit();
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
				dir.toFile().deleteOnExit();
				return FileVisitResult.CONTINUE;
			}
		});
	}
	
	public static File createTempDir(String dirName) {
		return new File(getTempDirectoryPath(), dirName);
	}
	
	public static String getTempDirectoryPath() {
		return System.getProperty("java.io.tmpdir");
	}
	
	public static URL[] fileDirectoriesToUrls(String... fileDirectories) throws MalformedURLException {
		URL[] urls = new URL[fileDirectories.length];
		int i = 0;
		for (String fileDirectory : fileDirectories) {
			urls[i++] = new File(fileDirectory).toURI().toURL();
		}
		return urls;
	}
}
