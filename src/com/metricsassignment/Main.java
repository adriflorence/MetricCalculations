package com.metricsassignment;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.metricsassignment.metrics.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static List<File> files = new ArrayList<>();

    public static void main(String[] args) {

        File file = new File("CS409TestSystem2020");

        List<File> files = doListing(file);

        for(File f : files) {
            if(f.getName().endsWith(".java")){
                System.out.println(f.getPath());
                CompilationUnit compilationUnit = parse(f);

                List<Metric> metrics = new ArrayList<>();
                metrics.add(new WMCMetric());
//                metrics.add(new RFCMetric());
//                metrics.add(new CBOMetric());
//                metrics.add(new LCOMMetric());

                MetricReport mr = new MetricReport(compilationUnit);
                mr.aggregateData(metrics);
            }
        }

    }

    public static List<File> doListing(File dirName) {

        File[] fileList = dirName.listFiles();

        for (File file : fileList) {
            if (file.isFile()) {
                files.add(file);
            } else if (file.isDirectory()) {
                files.add(file);
                doListing(file);
            }
        }

        return files;
    }

    public static CompilationUnit parse(File file) {

        try {
            return StaticJavaParser.parse(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
