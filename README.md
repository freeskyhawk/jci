# jci
JCI "Java Code Injector" is a simple to use java library providing a set of APIs to dynamically "on the fly" generate java source(s) compile them to java classes and (re-)load them at run time.

## Usage

    String sourceRoot = "mydir/src";
    String classRoot = "mydir/bin";
    String code = 
        "package com.af.jci.examples;" +
        "public class Example1HelloWorld {" +
        "	public Example1HelloWorld() {System.out.println(\"This is a hello world from a class Constructor generated on the fly !!\");}" +
        "}";
    
    JavaSource javaSource = new StringToJavaSourceAdapter(sourceRoot).adapt(code);
    
    DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
    
    boolean success = JaveSourceCompiler.getInstance().compile(javaSource, classRoot, diagnostics);
    
    if (!success) {
        ClassLoader classLoader = new ClassReloader(FileUtil.fileDirectoriesToUrls(classRoot));
        Class<?> loadedClass = classLoader.loadClass(javaSource.getFullyQualifiedName());
        loadedClass.newInstance();
    }
    
## Examples
Check out the examples package in src/test/java/com/af/lib/jci for more full usage examples.
