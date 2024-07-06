package com.algorithmfusion.libs.file;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Utility class providing several functions for handling temporary files.
 * 
 * @author Hallo Khaznadar
 */
public class FileUtil {

	/**
	 * The class should not be instantiated. Otherwise it will through an exception.
	 * 
	 * @throws InstantiationException
	 */
	protected FileUtil() throws InstantiationException {
	    throw new InstantiationException("Instances of this type are forbidden.");
	}
	
	/**
	 * Retrieves the path of configured OS specific TEMP directory.
	 *   
	 * @return Path of TEMP directory
	 */
	public static String getTempDirectoryPath() {
		return System.getProperty("java.io.tmpdir");
	}
	
	/**
	 * Creates a directory inside the configured OS specific TEMP directory.
	 * 
	 * @param dirName
	 * @return
	 */
	public static File createTempDir(String dirName) {
		return new File(getTempDirectoryPath(), dirName);
	}
	
	/**
	 * Converts the given file directories to the corresponding URL.
	 *  
	 * @param fileDirectories
	 * @return URL[]
	 * @throws MalformedURLException
	 */
	public static URL[] fileDirectoriesToUrls(String... fileDirectories) throws MalformedURLException {
		URL[] urls = new URL[fileDirectories.length];
		int i = 0;
		for (String fileDirectory : fileDirectories) {
			urls[i++] = new File(fileDirectory).toURI().toURL();
		}
		return urls;
	}
	
	/**
	 * Make sure that the given File get deleted on exit of the current process.
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static void recursiveDeleteOnExit(File file) throws IOException {
		recursiveDeleteOnExit(file.toPath());
	}
	
	/**
	 * Make sure that the file under the given Path get deleted on exit of the current process.
	 * 
	 * @param path
	 * @throws IOException
	 */
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
}
