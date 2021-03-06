package org.smveloso.otof.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestUtil {

    private static final Logger logger = LoggerFactory.getLogger(TestUtil.class);
    
    final String classpath = System.getProperty("java.class.path");
    final String pathSeparator = System.getProperty("path.separator");

    public File searchFileInClasspath(String fileName) throws Exception {
        logger.trace(">> searchFileInClasspath(String) " + fileName);
        final StringTokenizer tokenizer = new StringTokenizer(classpath, pathSeparator);

        while (tokenizer.hasMoreTokens()) {
            final String pathElement = tokenizer.nextToken();
            final File file = new File(pathElement);
            
            File target = null;
            if (file.isDirectory()) {
                target = findFileInDirectory(file,fileName);
            } else if (file.getName().equals(fileName)) {                
                target = file;
            } 
            
            if (null == target) {
                throw new Exception("File not found: " + fileName);
            }
              
            return target;
            
        }
            
        throw new Exception("File not found in classpath: " + fileName);
    }

    public File findFileInDirectory(File directory, String fileName) throws Exception {
            
        if (!directory.isDirectory()) {
            throw new Exception("Not a directory: " + directory.getPath());
        }
        
        File target = null;
        List<File> directories = new ArrayList<>();
        
        for (File file:directory.listFiles()) {
            if (file.isFile()) {
                if (file.getName().equals(fileName)) {
                    return file;
                }
            } else if (file.isDirectory()) {
                directories.add(file);
            }            
        }
        
        for (File subDirectory:directories) {
            target = findFileInDirectory(subDirectory, fileName);
            if (null != target) {
                return target;
            }
        }
        
        return target;
    }

    public boolean compareDateOnly(Date first, Date second) {
        Calendar calFirst = new GregorianCalendar();
        calFirst.setTime(first);
        
        Calendar calSecond = new GregorianCalendar();
        calSecond.setTime(second);
        
        return ( calFirst.get(Calendar.YEAR) == calSecond.get(Calendar.YEAR) ) &&
               ( calFirst.get(Calendar.MONTH) == calSecond.get(Calendar.MONTH) ) && 
               ( calFirst.get(Calendar.DAY_OF_MONTH) == calSecond.get(Calendar.DAY_OF_MONTH) );
        
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