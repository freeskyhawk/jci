package com.algorithmfusion.libs.jci.jsg.impl;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.algorithmfusion.libs.jci.jsg.api.JavaSource;
import com.algorithmfusion.libs.jci.jsg.api.JavaSourceAdapter;

public class StringToJavaSourceAdapter extends JavaSourceAdapter<String> {

	private static final Pattern classNamePattern = Pattern.compile("\\s*class\\s+(\\w+)\\s+((extends\\s+\\w+)|(implements\\s+\\w+( ,\\w+)*))?\\s*\\{");
	private static final Pattern packageNamePattern  = Pattern.compile("\\s*package\\s+([\\w\\.]+)\\s*\\;");
	
	private String sourceRoot;
	
	public StringToJavaSourceAdapter(File sourceRootFile) {
		this(sourceRootFile.getPath());
	}
	
	public StringToJavaSourceAdapter(String sourceRoot) {
		this.sourceRoot = sourceRoot;
	}
	
	@Override
	public JavaSource adapt(String sourceCode) {
		String sourceCodeWithNoComments = sourceCode.replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)","");
		String name = matchPattern(classNamePattern, sourceCodeWithNoComments, 1);
		String pkg = matchPattern(packageNamePattern, sourceCodeWithNoComments, 1);
		return new FileBasedJavaSource.Builder().sourceRoot(sourceRoot).pkg(pkg).name(name).code(sourceCode).build();
	}
	
	private static String matchPattern(Pattern pattern, String source, int groupToMatch) {
		Matcher matcher = pattern.matcher(source);
		if(matcher.find()) {
			return matcher.group(groupToMatch);
		}
		return "";
	}
}
