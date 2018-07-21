package org.smveloso.otof.test;

import java.io.File;
import java.util.StringTokenizer;

public class TestUtil {

    final String classpath = System.getProperty("java.class.path");
    final String pathSeparator = System.getProperty("path.separator");

    public File searchFileInClasspath(String fileName) throws Exception {
     
        final StringTokenizer tokenizer = new StringTokenizer(classpath, pathSeparator);

        while (tokenizer.hasMoreTokens()) {
            final String pathElement = tokenizer.nextToken();
            final File directoryOrJar = new File(pathElement);
            final File absoluteDirectoryOrJar = directoryOrJar.getAbsoluteFile();
            if (absoluteDirectoryOrJar.isFile()) {
                final File target = new File(absoluteDirectoryOrJar.getParent(), fileName);
                if (target.exists()) {
                    return target;
                }
            } else {
                final File target = new File(directoryOrJar, fileName);
                if (target.exists()) {
                    return target;
                }
            }
        }
        throw new Exception("File not found in classpath: " + fileName);
    }

    
    
    private static TestUtil instance = null;
    
    private TestUtil() {
    }

    public static synchronized TestUtil getInstance() {
        if (null == instance) {
            instance = new TestUtil();
        }
        return instance;    
    }
    
}