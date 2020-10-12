package com.metricsassignment;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.metricsassignment.metrics.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        String path = "CS409TestSystem2020/foxes-and-rabbits-graph/Animal.java";
        CompilationUnit cu = parse(path);

        // WMC, RFC, CBO, LCOM
        List<Metric> metrics = new ArrayList<>();
        metrics.add(new WMCMetric());
        metrics.add(new RFCMetric());
        metrics.add(new CBOMetric());
        metrics.add(new LCOMMetric());

        MetricReport mr = new MetricReport();
        mr.aggregateData(metrics);
    }

    public static CompilationUnit parse(String filePath) {

        try {
            return StaticJavaParser.parse(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
