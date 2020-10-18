package com.metricsassignment.IO;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JavaFileFilter {

    /**
     * Recursively returns all only the java files from the input directory
     */
    public List<File> filterJavaFiles(File dirName) {
        List<File> javaFiles = new ArrayList<>();
        File[] fileList = dirName.listFiles();

        if (fileList != null) {
            for (File file : fileList) {
                if (file.isFile() && file.getName().endsWith(".java")) {
                    javaFiles.add(file);
                } else if (file.isDirectory()) {
                    javaFiles.addAll(filterJavaFiles(file));
                }
            }
        }
        return javaFiles;
    }
}
