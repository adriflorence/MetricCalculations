package com.metricsassignment.metrics;

import com.github.javaparser.ast.CompilationUnit;

// Lack of Cohesion of Methods
public class LCOMMetric implements Metric {

    @Override
    public double calculate(CompilationUnit compilationUnit) {
        return 1;
    }
}
