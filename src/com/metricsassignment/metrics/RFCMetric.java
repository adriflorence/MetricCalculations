package com.metricsassignment.metrics;

import com.github.javaparser.ast.CompilationUnit;

// the size of the Response set of a class
public class RFCMetric implements Metric {

    // the number of methods in a class
    // plus the number of remote methods called by methods in the class
    @Override
    public double calculate(CompilationUnit compilationUnit) {
        return 0;
    }
}
