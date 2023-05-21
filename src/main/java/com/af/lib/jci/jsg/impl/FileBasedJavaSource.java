package com.af.lib.jci.jsg.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import com.af.lib.jci.jsg.api.JavaSource;

public class FileBasedJavaSource extends StringBasedJavaSource implements JavaSource {

	private Optional<String> sourceRoot;

	private FileBasedJavaSource(Builder builder) {
		super(builder);
		this.sourceRoot = builder.sourceRoot;
	}

	@Override
	public String getSourceRoot() {
		return sourceRoot.orElse(null);
	}

	@Override
	public File file() {
		return new File(getSourceRoot(), getFullSourceNameWithPackage());
	}

	public void persist() throws IOException {
		File sourceFile = file();
		sourceFile.getParentFile().mkdirs();
		Files.write(sourceFile.toPath(), getCode().getBytes());
	}

	public static class Builder extends StringBasedJavaSource.Builder {

		private Optional<String> sourceRoot;

		public Builder() {
			this.sourceRoot = Optional.empty();
		}

		public Builder pkg(String pkg) {
			super.pkg(pkg);
			return this;
		}

		public Builder name(String name) {
			super.name(name);
			return this;
		}

		public Builder code(String code) {
			super.code(code);
			return this;
		}
		public Builder sourceRoot(String sourceRoot) {
			this.sourceRoot = Optional.ofNullable(sourceRoot);
			return this;
		}

		public FileBasedJavaSource build() {
			return new FileBasedJavaSource(this);
		}
	}
}